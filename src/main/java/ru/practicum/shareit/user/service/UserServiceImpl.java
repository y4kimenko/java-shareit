package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.request.UserRequestCreateDto;
import ru.practicum.shareit.user.dto.request.UserRequestUpdateDto;
import ru.practicum.shareit.user.exception.DuplicateEmailException;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserStorage;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public void deleteById(long id) {
        userStorage.delete(id);
    }

    @Override
    public UserDto findById(long id) {
        User user = userStorage.findById(id).orElseThrow(() -> new UserNotFoundException(
                "Пользователь с id = " + id + " не найден"));

        return UserMapper.toResponseDto(user);
    }

    @Override
    public UserDto update(UserRequestUpdateDto dto, long id) {
        User req = UserMapper.toEntity(dto, id); // не уверен до конца нужно ли здесь преобразование в доменный объект

        User user = userStorage.findById(id).orElseThrow(() -> new UserNotFoundException(
                "Пользователь с id = " + id + " не найден"));

        if (req.getName() != null)
            user.setName(req.getName());

        if (req.getEmail() != null) {
            userStorage.findByEmail(dto.email()).ifPresent(existing -> {
                throw new DuplicateEmailException("Пользователь с таким email уже существует");
            });

            user.setEmail(req.getEmail());
        }


        return UserMapper.toResponseDto(userStorage.update(user));
    }

    @Override
    public UserDto create(UserRequestCreateDto dto) {
        userStorage.findByEmail(dto.email()).ifPresent(existing -> {
            throw new DuplicateEmailException("Пользователь с таким email уже существует");
        });

        User user = UserMapper.toEntity(dto);
        return UserMapper.toResponseDto(userStorage.create(user));
    }
}
