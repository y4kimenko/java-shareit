package ru.practicum.shareit.booking.repository.projection;

import java.time.LocalDateTime;

public record BookingShortProjection(
        Long itemId,
        Long bookingId,
        Long bookerId,
        LocalDateTime startAt
) {
}
