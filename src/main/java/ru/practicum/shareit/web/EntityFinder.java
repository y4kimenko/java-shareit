package ru.practicum.shareit.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.exception.NotFoundBooking;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class EntityFinder {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;

    public Booking getBookingOrThrow(long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundBooking(bookingId)
        );
    }

    public User getUserOrThrow(long Userid) {
        return userRepository.findById(Userid).orElseThrow(
                () -> new UserNotFoundException(Userid)
        );
    }

    public Item getItemOrThrow(long itemId) {
        return itemRepository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException(itemId)
        );
    }
}
