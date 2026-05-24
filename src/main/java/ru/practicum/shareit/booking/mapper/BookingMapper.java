package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.request.BookingRequestCreateDto;
import ru.practicum.shareit.booking.dto.response.BookingResponseDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.response.ItemResponseShortDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.response.UserResponseShortDto;


public class BookingMapper {
    public static BookingResponseDto toResponseDto(Booking booking) {
        return new BookingResponseDto(
                booking.getId(),
                booking.getStartAt(),
                booking.getEndAt(),
                booking.getStatus(),
                new UserResponseShortDto(
                        booking.getBooker().getId()
                ),
                new ItemResponseShortDto(
                        booking.getItem().getId(),
                        booking.getItem().getName()
                )
        );
    }

    public static Booking toEntity(
            BookingRequestCreateDto dto,
            Item item,
            User booker
    ) {
        return new Booking(
                dto.start(),
                dto.end(),
                item,
                booker
        );
    }

}
