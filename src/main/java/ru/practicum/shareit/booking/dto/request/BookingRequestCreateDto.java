package ru.practicum.shareit.booking.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BookingRequestCreateDto(
        @NotNull
        Long itemId,

        @NotNull
        @FutureOrPresent
        LocalDateTime start,

        @NotNull
        @FutureOrPresent
        LocalDateTime end
) {
}
