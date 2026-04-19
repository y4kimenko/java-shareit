package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.request.ItemRequestCreateDto;
import ru.practicum.shareit.item.dto.request.ItemRequestData;
import ru.practicum.shareit.item.dto.request.ItemRequestUpdateDto;
import ru.practicum.shareit.item.model.Item;


public class ItemMapper {
    public static ItemDto toResponseDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable()
        );
    }

    public static Item toEntity(ItemRequestUpdateDto dto, long id) {
        if (dto == null) return null;

        Item item = new Item();

        item.setId(id);
        applyToEntity(dto, item);

        return item;
    }

    public static Item toEntity(ItemRequestCreateDto dto) {
        if (dto == null) return null;

        Item item = new Item();

        applyToEntity(dto, item);

        return item;
    }

    public static void applyToEntity(ItemRequestData dto, Item target) {
        if (dto == null || target == null) return;

        target.setName(dto.name());
        target.setDescription(dto.description());
        target.setAvailable(dto.available());

    }
}
