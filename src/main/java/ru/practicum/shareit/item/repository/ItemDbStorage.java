package ru.practicum.shareit.item.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ItemDbStorage implements ItemRepository {
    private final Map<Long, Item> storage = new HashMap<>();


    @Override
    public Item create(Item item) {
        log.debug("createItem() – request name={}, description={}, available={}", item.getName(), item.getDescription(), item.getAvailable());
        long t0 = System.nanoTime();

        item.setId(getNextId());
        storage.put(item.getId(), item);

        long ms = (System.nanoTime() - t0) / 1_000_000;
        log.info("createItem() – created id={} in {} ms", item.getId(), ms);

        return item;
    }

    @Override
    public Item update(Item item) {

        log.debug("updateItem() – request id={}, name={}, description={}, available={}",
                item.getId(), item.getName(), item.getDescription(), item.getAvailable());
        long t0 = System.nanoTime();

        if (storage.containsKey(item.getId())) {
            storage.put(item.getId(), item);

            long ms = (System.nanoTime() - t0) / 1_000_000;
            log.info("updateItem() – updated id={} in {} ms, updateItem={}", item.getId(), ms, item);

            return item;
        }
        throw new ItemNotFoundException("Item not found");
    }

    @Override
    public Optional<Item> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Item> getUserItems(long userId) {
        return storage.values().stream()
                .filter(it -> it.getOwner().getId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> searchByText(String text) {
        String query = text.toLowerCase();
        return storage.values().stream()
                .filter(it -> it.getAvailable().equals(true))
                .filter(it -> it.getName().toLowerCase().contains(query)
                        || it.getDescription().toLowerCase().contains(query))
                .toList();
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
