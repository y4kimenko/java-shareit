package ru.practicum.shareit.user.service;


import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.request.UserRequestCreateDto;
import ru.practicum.shareit.user.dto.request.UserRequestUpdateDto;


public interface UserService {
    void deleteById(long id);

    UserDto findById(long id);

    UserDto update(UserRequestUpdateDto dto, long id);

    UserDto create(UserRequestCreateDto dto);
}
