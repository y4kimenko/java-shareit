package ru.practicum.shareit.comment.controller;


import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.comment.dto.CommentRequestCreateDto;
import ru.practicum.shareit.comment.dto.CommentResponseDto;
import ru.practicum.shareit.comment.service.CommentService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(@PathVariable
                                            @PositiveOrZero(message = "itemId не может быть отрицательным")
                                            Long itemId,
                                            @RequestHeader(name = "X-Sharer-User-Id")
                                            @PositiveOrZero(message = "X-Sharer-User-Id не может быть отрицательным")
                                            Long userId,
                                            @RequestBody
                                            CommentRequestCreateDto dto
    ) {
        return commentService.createComment(itemId, userId, dto);
    }
}