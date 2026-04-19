package ru.practicum.shareit.item.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemRequestCreateDto(
        @NotBlank(message = "name не должно быть пустым")
        String name,
        @NotBlank(message = "description не должно быть пустым")
        String description,
        @NotNull(message = "available не должно быть пустым")
        Boolean available
) implements ItemRequestData {
}
