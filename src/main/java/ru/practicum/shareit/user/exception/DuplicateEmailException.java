package ru.practicum.shareit.user.exception;

import ru.practicum.shareit.web.exception.ConflictException;


public class DuplicateEmailException extends ConflictException {
    public DuplicateEmailException() {
        super("Пользователь с таким email уже существует");
    }
}
