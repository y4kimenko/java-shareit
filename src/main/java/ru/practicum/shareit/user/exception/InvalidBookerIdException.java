package ru.practicum.shareit.user.exception;

public class InvalidBookerIdException extends RuntimeException {
    public InvalidBookerIdException(long id) {
        super("Пользователь с id = " + id + " неправильно задан");
    }
}
