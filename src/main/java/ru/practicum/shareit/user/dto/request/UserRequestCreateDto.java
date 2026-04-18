package ru.practicum.shareit.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


public record UserRequestCreateDto(
        String name,

        @NotEmpty(message = "email не может быть пустым")
        @NotBlank(message = "email не должно состоять из пробелов")
        @Email(message = "E-mail  is incorrect")
        String email
) implements UserRequestData {
}
