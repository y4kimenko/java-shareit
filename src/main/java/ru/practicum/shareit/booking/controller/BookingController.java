package ru.practicum.shareit.booking.controller;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.dto.request.BookingRequestCreateDto;
import ru.practicum.shareit.booking.dto.response.BookingResponseDto;
import ru.practicum.shareit.booking.enums.BookingStatusRequest;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponseDto createBooking(
            @Validated
            @RequestBody
            BookingRequestCreateDto dto,
            @RequestHeader(name = "X-Sharer-User-Id")
            @PositiveOrZero(message = "X-Sharer-User-Id не может быть отрицательным")
            Long userId) {
        return bookingService.createBooking(dto, userId);
    }

    @PatchMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public BookingResponseDto approveBooking(
            @PathVariable("bookingId")
            @PositiveOrZero(message = "bookingId не может быть отрицательным") final Long bookingId,
            @RequestParam("approved")
            boolean approved,
            @RequestHeader(name = "X-Sharer-User-Id")
            @PositiveOrZero(message = "X-Sharer-User-Id не может быть отрицательным")
            Long userId
    ) {
        return bookingService.approveBooking(userId, approved, bookingId);
    }

    @GetMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public BookingResponseDto getBooking(
            @PathVariable("bookingId")
            @PositiveOrZero(message = "bookingId не может быть отрицательным") final Long bookingId,
            @RequestHeader(name = "X-Sharer-User-Id")
            @PositiveOrZero(message = "X-Sharer-User-Id не может быть отрицательным")
            Long userId
    ) {
        return bookingService.getBooking(userId, bookingId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookingResponseDto> getBookingsBooker(
            @RequestHeader(name = "X-Sharer-User-Id")
            @PositiveOrZero(message = "X-Sharer-User-Id не может быть отрицательным")
            Long userId,
            @RequestParam(name = "state", defaultValue = "ALL")
            BookingStatusRequest state
    ) {
        return bookingService.getBookingBooker(userId, state);
    }

    @GetMapping("/owner")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingResponseDto> getBookingsOwner(
            @RequestHeader(name = "X-Sharer-User-Id")
            @PositiveOrZero(message = "X-Sharer-User-Id не может быть отрицательным")
            Long userId,
            @RequestParam(name = "state", defaultValue = "ALL")
            BookingStatusRequest state
    ) {
        return bookingService.getBookingOwner(userId, state);
    }


}
