package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.response.BookingShortResponseDto;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.comment.dto.CommentResponseDto;
import ru.practicum.shareit.comment.repository.CommentRepository;
import ru.practicum.shareit.item.dto.request.ItemRequestCreateDto;
import ru.practicum.shareit.item.dto.request.ItemRequestUpdateDto;
import ru.practicum.shareit.item.dto.response.ItemResponseDto;
import ru.practicum.shareit.item.dto.response.ItemWithPeriodAndCommentsResponseDto;
import ru.practicum.shareit.item.dto.response.ItemWithPeriodResponseDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.web.EntityFinder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final EntityFinder entityFinder;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    @Override
    public ItemResponseDto createItem(ItemRequestCreateDto dto, long userId) {

        User user = entityFinder.getUserOrThrow(userId);

        Item req = ItemMapper.toEntity(dto);
        req.setOwner(user);

        return ItemMapper.toItemResponseDto(itemRepository.save(req));
    }

    @Override
    public ItemResponseDto updateItem(ItemRequestUpdateDto dto, long itemId, long userId) {

        User user = entityFinder.getUserOrThrow(userId);

        Item req = ItemMapper.toEntity(dto, userId);
        req.setOwner(user);

        Item item = entityFinder.getItemOrThrow(itemId);

        if (req.getName() != null)
            item.setName(req.getName());
        if (req.getDescription() != null)
            item.setDescription(req.getDescription());
        if (req.getAvailable() != null)
            item.setAvailable(req.getAvailable());

        return ItemMapper.toItemResponseDto(itemRepository.save(item));
    }

    @Override
    public ItemWithPeriodAndCommentsResponseDto findById(long itemId) {
        Item item = entityFinder.getItemOrThrow(itemId);

        List<CommentResponseDto> comments = commentRepository.getCommentsByItemId(itemId);

        LocalDateTime now = LocalDateTime.now();

        List<BookingShortResponseDto> bookings = bookingRepository.findLastAndNextBookingsMapByItemIds(
                        List.of(itemId),
                        now).values().stream()
                .findFirst()
                .orElse(List.of());

        return ItemMapper.toItemWithPeriodAndCommentsResponseDto(item, bookings, comments);
    }

    @Override
    public List<ItemWithPeriodResponseDto> getUserItems(long userId) {
        entityFinder.getUserOrThrow(userId);

        List<Item> items = itemRepository.getItemsByOwner_Id(userId);

        Map<Long, List<BookingShortResponseDto>> itemsPeriods = bookingRepository.findLastAndNextBookingsMapByItemIds(
                items.stream().map(Item::getId).toList(),
                LocalDateTime.now()
        );

        return items.stream()
                .map(item -> ItemMapper.toItemWithPeriodResponseDto(item,
                        itemsPeriods.getOrDefault(item.getId(), List.of()))
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemResponseDto> searchItems(String query) {
        return query.trim().isEmpty() ? List.of() : itemRepository.getItemsByQuery(query).stream()
                .map(ItemMapper::toItemResponseDto)
                .toList();
    }


}
