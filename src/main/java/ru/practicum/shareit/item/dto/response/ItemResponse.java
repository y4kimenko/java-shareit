package ru.practicum.shareit.item.dto.response;

public interface ItemResponse {
    long id();

    String name();

    String description();

    boolean available();
}
