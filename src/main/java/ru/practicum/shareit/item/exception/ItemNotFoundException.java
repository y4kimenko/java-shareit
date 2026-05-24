package ru.practicum.shareit.item.exception;

import ru.practicum.shareit.web.exception.NotFoundException;

public class ItemNotFoundException extends NotFoundException {
    public ItemNotFoundException(long id) {
        super("Вещь с id = " + id + " не найдена");
    }
}