package ru.practicum.shareit.item.mapper;


import ru.practicum.shareit.booking.dto.response.BookingShortResponseDto;
import ru.practicum.shareit.comment.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.request.ItemRequestCreateDto;
import ru.practicum.shareit.item.dto.request.ItemRequestData;
import ru.practicum.shareit.item.dto.request.ItemRequestUpdateDto;
import ru.practicum.shareit.item.dto.response.ItemResponse;
import ru.practicum.shareit.item.dto.response.ItemResponseDto;
import ru.practicum.shareit.item.dto.response.ItemWithPeriodAndCommentsResponseDto;
import ru.practicum.shareit.item.dto.response.ItemWithPeriodResponseDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;


public class ItemMapper {
    public static ItemResponseDto toItemResponseDto(Item item) {
        return mapBase(item, ItemResponseDto::new);
    }

    public static ItemWithPeriodResponseDto toItemWithPeriodResponseDto(
            Item item,
            List<BookingShortResponseDto> booking
    ) {
        BookingShortResponseDto lastBooking = booking.size() == 2 ? booking.getFirst() : null;
        BookingShortResponseDto nextBooking = booking.size() == 2 ? booking.getLast() : null;

        return mapBase(item, (id, name, description, available) ->
                new ItemWithPeriodResponseDto(
                        id,
                        name,
                        description,
                        available,
                        lastBooking,
                        nextBooking
                )
        );
    }

    public static ItemWithPeriodAndCommentsResponseDto toItemWithPeriodAndCommentsResponseDto(
            Item item,
            List<BookingShortResponseDto> booking,
            List<CommentResponseDto> comments
    ) {
        BookingShortResponseDto lastBooking = booking.size() == 2 ? booking.getFirst() : null;
        BookingShortResponseDto nextBooking = booking.size() == 2 ? booking.getLast() : null;

        return mapBase(item, (id, name, description, available) ->
                new ItemWithPeriodAndCommentsResponseDto(
                        id,
                        name,
                        description,
                        available,
                        lastBooking,
                        nextBooking,
                        comments
                )
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

    private static <T extends ItemResponse> T mapBase(
            Item item,
            ItemBaseDtoFactory<T> factory
    ) {
        return factory.create(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable()
        );
    }

    @FunctionalInterface
    private interface ItemBaseDtoFactory<T extends ItemResponse> {
        T create(
                Long id,
                String name,
                String description,
                boolean available
        );
    }
}
