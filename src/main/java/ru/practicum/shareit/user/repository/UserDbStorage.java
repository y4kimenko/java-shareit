package ru.practicum.shareit.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.exception.UserNotFoundException;

import java.util.HashMap;
import java.util.Optional;

@Repository
@Slf4j
public class UserDbStorage implements UserStorage { // Реализовал в виде обычной заглушки, так как не знаю какое тз будет дальше
    private final HashMap<Long, User> storage = new HashMap<>();

    @Override
    public User create(User user) {
        long t0 = System.nanoTime();

        log.debug("createUser() – request name={}, email={}", user.getName(), user.getEmail());
        user.setId(getNextId());

        storage.put(user.getId(), user);

        long ms = (System.nanoTime() - t0) / 1_000_000;
        log.info("createUser() – created id={} in {} ms", user.getId(), ms);

        return user;
    }

    @Override
    public void delete(long id) {
        storage.remove(id);
    }

    @Override
    public User update(User user) {
        long t0 = System.nanoTime();
        log.debug("updateUser() – request id={}, name={}, email={}",
                user.getId(), user.getName(), user.getEmail());


        if (storage.containsKey(user.getId())) {
            storage.put(user.getId(), user);

            long ms = (System.nanoTime() - t0) / 1_000_000;
            log.info("updateUser() – updated id={} in {} ms, updateUser={}", user.getId(), ms, user);

            return user;
        }
        throw new UserNotFoundException("User not found");
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return storage.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }


    // Генерация нового Id
    private long getNextId() {
        long currentMaxId = storage.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
