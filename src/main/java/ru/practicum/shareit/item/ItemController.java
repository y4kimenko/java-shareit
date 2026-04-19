package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.request.ItemRequestCreateDto;
import ru.practicum.shareit.item.dto.request.ItemRequestUpdateDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;


/**
 * TODO Sprint add-controllers.
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto createItem(@Valid
                              @NotNull
                              @RequestBody final ItemRequestCreateDto dto,
                              @RequestHeader(name = "X-Sharer-User-Id")
                              @PositiveOrZero(message = "X-Sharer-User-Id не может быть отрицательным")
                              Long userId) {
        return itemService.createItem(dto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@Valid
                              @NotNull
                              @RequestBody
                              ItemRequestUpdateDto dto,
                              @PathVariable
                              @PositiveOrZero(message = "itemId не может быть отрицательным")
                              Long itemId,
                              @RequestHeader(name = "X-Sharer-User-Id")
                              @PositiveOrZero(message = "X-Sharer-User-Id не может быть отрицательным")
                              Long userId) {
        return itemService.updateItem(dto, itemId, userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto findById(@PathVariable
                            @PositiveOrZero(message = "itemId не может быть отрицательным")
                            Long itemId) {
        return itemService.findById(itemId);
    }

    @GetMapping
    public List<ItemDto> findUserItems(@RequestHeader(name = "X-Sharer-User-Id")
                                       @PositiveOrZero(message = "X-Sharer-User-Id не может быть отрицательным")
                                       Long userId) {
        return itemService.getUserItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam("text") String query) {
        return itemService.searchItems(query);
    }

}
