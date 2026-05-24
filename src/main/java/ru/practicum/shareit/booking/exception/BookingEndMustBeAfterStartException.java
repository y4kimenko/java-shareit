package ru.practicum.shareit.booking.exception;

public class BookingEndMustBeAfterStartException extends RuntimeException {
    public BookingEndMustBeAfterStartException() {
        super("Конец бронирования должен быть позже, чем его начало.");
    }
}
