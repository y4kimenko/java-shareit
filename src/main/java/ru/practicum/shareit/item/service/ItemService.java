package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.request.ItemRequestCreateDto;
import ru.practicum.shareit.item.dto.request.ItemRequestUpdateDto;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemRequestCreateDto dto, long userId);

    ItemDto updateItem(ItemRequestUpdateDto dto, long itemId, long userId);

    ItemDto findById(long itemId);

    List<ItemDto> getUserItems(long userId);

    List<ItemDto> searchItems(String query);
}
