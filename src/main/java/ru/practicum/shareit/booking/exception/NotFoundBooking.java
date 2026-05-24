package ru.practicum.shareit.booking.exception;

import ru.practicum.shareit.web.exception.NotFoundException;

public class NotFoundBooking extends NotFoundException {
    public NotFoundBooking(long id) {
        super("Бронирование с " + id + " не было найдено.");
    }
}
