package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.request.ItemRequestCreateDto;
import ru.practicum.shareit.item.dto.request.ItemRequestUpdateDto;
import ru.practicum.shareit.item.dto.response.ItemResponseDto;
import ru.practicum.shareit.item.dto.response.ItemWithPeriodAndCommentsResponseDto;
import ru.practicum.shareit.item.dto.response.ItemWithPeriodResponseDto;

import java.util.List;

public interface ItemService {
    ItemResponseDto createItem(ItemRequestCreateDto dto, long userId);

    ItemResponseDto updateItem(ItemRequestUpdateDto dto, long itemId, long userId);

    ItemWithPeriodAndCommentsResponseDto findById(long itemId);

    List<ItemWithPeriodResponseDto> getUserItems(long userId);

    List<ItemResponseDto> searchItems(String query);
}
