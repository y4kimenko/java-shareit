package ru.practicum.shareit.comment.mapper;

import ru.practicum.shareit.comment.dto.CommentResponseDto;
import ru.practicum.shareit.comment.model.Comment;

public class CommentMapper {
    public static CommentResponseDto toDto(final Comment c) {
        return new CommentResponseDto(
                c.getId(),
                c.getText(),
                c.getAuthor().getName(),
                c.getCreatedAt()
        );
    }
}
