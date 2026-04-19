package ru.practicum.shareit.item.dto;

public record ItemDto(
        long id,
        String name,
        String description,
        boolean available
) {
}
