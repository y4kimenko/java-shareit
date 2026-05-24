package ru.practicum.shareit.comment.service;

import ru.practicum.shareit.comment.dto.CommentRequestCreateDto;
import ru.practicum.shareit.comment.dto.CommentResponseDto;

public interface CommentService {
    CommentResponseDto createComment(long userId, long itemId, CommentRequestCreateDto dto);
}
