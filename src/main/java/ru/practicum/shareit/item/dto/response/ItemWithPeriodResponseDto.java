package ru.practicum.shareit.item.dto.response;

import ru.practicum.shareit.booking.dto.response.BookingShortResponseDto;

public record ItemWithPeriodResponseDto(
        long id,
        String name,
        String description,
        boolean available,
        BookingShortResponseDto lastBooking,
        BookingShortResponseDto nextBooking
) implements ItemResponse {
}
