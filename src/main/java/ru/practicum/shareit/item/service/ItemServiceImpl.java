package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.request.ItemRequestCreateDto;
import ru.practicum.shareit.item.dto.request.ItemRequestUpdateDto;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.repository.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserStorage userStorage;
    private final ItemRepository itemRepository;

    @Override
    public ItemDto createItem(ItemRequestCreateDto dto, long userId) {

        User user = getUserOrThrow(userId);

        Item req = ItemMapper.toEntity(dto);
        req.setOwner(user);

        return ItemMapper.toResponseDto(itemRepository.create(req));
    }

    @Override
    public ItemDto updateItem(ItemRequestUpdateDto dto, long itemId, long userId) {

        User user = getUserOrThrow(userId);

        Item req = ItemMapper.toEntity(dto, userId);
        req.setOwner(user);

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(
                "Вещь с id = " + itemId + " не найдена"));

        if (req.getName() != null)
            item.setName(req.getName());
        if (req.getDescription() != null)
            item.setDescription(req.getDescription());
        if (req.getAvailable() != null)
            item.setAvailable(req.getAvailable());

        return ItemMapper.toResponseDto(itemRepository.update(item));
    }

    @Override
    public ItemDto findById(long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(
                "Вещь с id = " + itemId + " не найдена"));

        return ItemMapper.toResponseDto(item);
    }

    @Override
    public List<ItemDto> getUserItems(long userId) {
        getUserOrThrow(userId);
        return itemRepository.getUserItems(userId).stream()
                .map(ItemMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<ItemDto> searchItems(String query) {
        return query.trim().isEmpty() ? List.of() : itemRepository.searchByText(query).stream()
                .map(ItemMapper::toResponseDto)
                .toList();
    }


    private User getUserOrThrow(long userId) {
        return userStorage.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id = " + userId + " не найден"));
    }

}
