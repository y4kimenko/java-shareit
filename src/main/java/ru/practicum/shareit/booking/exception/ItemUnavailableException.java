package ru.practicum.shareit.booking.exception;

public class ItemUnavailableException extends RuntimeException {
    public ItemUnavailableException(Long id) {
        super(
                "Вещь с id=" + id + " сейчас недоступна."
        );
    }
}
