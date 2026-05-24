package ru.practicum.shareit.item.dto.response;


public record ItemResponseDto(
        long id,
        String name,
        String description,
        boolean available

) implements ItemResponse {
}
