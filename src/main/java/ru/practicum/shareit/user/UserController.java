package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.request.UserRequestCreateDto;
import ru.practicum.shareit.user.dto.request.UserRequestUpdateDto;
import ru.practicum.shareit.user.service.UserService;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid
                              @NotNull
                              @RequestBody final UserRequestCreateDto dto) {
        return userService.create(dto);
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@Valid
                              @NotNull
                              @RequestBody
                              UserRequestUpdateDto dto,
                              @PathVariable("userId")
                              @PositiveOrZero(message = "userId не может быть отрицательным") final Long userId) {
        return userService.update(dto, userId);
    }


    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getById(@PathVariable("userId")
                           @PositiveOrZero(message = "userId не может быть отрицательным") final Long userId
    ) {
        return userService.findById(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("userId")
                           @PositiveOrZero(message = "userId не может быть отрицательным") final Long userId
    ) {
        userService.deleteById(userId);
    }
}
