package ru.practicum.shareit.item.dto.request;


import jakarta.validation.constraints.Pattern;


public record ItemRequestUpdateDto(
        @Pattern(
                regexp = ".*\\S.*",
                message = "name не должен быть пустым"
        )
        String name,

        @Pattern(
                regexp = ".*\\S.*",
                message = "description не должен быть пустым"
        )
        String description,

        Boolean available
) implements ItemRequestData {
}
