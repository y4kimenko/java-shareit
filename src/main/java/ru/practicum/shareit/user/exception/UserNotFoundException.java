package ru.practicum.shareit.user.exception;

import ru.practicum.shareit.web.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}