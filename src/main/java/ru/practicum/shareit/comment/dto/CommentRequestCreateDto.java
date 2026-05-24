package ru.practicum.shareit.comment.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentRequestCreateDto(
        @NotBlank
        String text
) {
}
