package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.request.BookingRequestCreateDto;
import ru.practicum.shareit.booking.dto.response.BookingResponseDto;
import ru.practicum.shareit.booking.enums.BookingStatusRequest;

import java.util.List;

public interface BookingService {
    BookingResponseDto createBooking(BookingRequestCreateDto bookingDto, long bookerId);

    BookingResponseDto approveBooking(long userId, boolean approved, long bookingId);

    BookingResponseDto getBooking(long userId, long bookingId);

    List<BookingResponseDto> getBookingOwner(long userId, BookingStatusRequest bookingStatus);

    List<BookingResponseDto> getBookingBooker(long userId, BookingStatusRequest bookingStatus);
}
