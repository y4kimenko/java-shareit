package ru.practicum.shareit.item.dto.response;

import ru.practicum.shareit.booking.dto.response.BookingShortResponseDto;
import ru.practicum.shareit.comment.dto.CommentResponseDto;

import java.util.List;

public record ItemWithPeriodAndCommentsResponseDto(
        long id,
        String name,
        String description,
        boolean available,
        BookingShortResponseDto lastBooking,
        BookingShortResponseDto nextBooking,
        List<CommentResponseDto> comments

) implements ItemResponse {
}
