package ru.practicum.shareit.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UserRequestUpdateDto(
        String name,

        @Email(message = "Некорректный формат email")
        @Pattern(
                regexp = ".*\\S.*",
                message = "Email не должен быть пустым"
        )
        String email) implements UserRequestData {
}
