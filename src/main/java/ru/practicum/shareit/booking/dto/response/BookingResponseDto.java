package ru.practicum.shareit.booking.dto.response;

import ru.practicum.shareit.booking.enums.BookingStatus;
import ru.practicum.shareit.item.dto.response.ItemResponseShortDto;
import ru.practicum.shareit.user.dto.response.UserResponseShortDto;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
public record BookingResponseDto(
        Long id,
        LocalDateTime start,
        LocalDateTime end,
        BookingStatus status,
        UserResponseShortDto booker,
        ItemResponseShortDto item
) {

}
