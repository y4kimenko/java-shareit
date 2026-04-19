package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.User;

import java.util.Optional;

public interface UserStorage {
    User create(User user);

    void delete(long id);

    User update(User user);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);
}
