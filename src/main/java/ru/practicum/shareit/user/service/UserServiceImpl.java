package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.request.UserRequestCreateDto;
import ru.practicum.shareit.user.dto.request.UserRequestUpdateDto;
import ru.practicum.shareit.user.exception.DuplicateEmailException;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.web.EntityFinder;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EntityFinder entityFinder;

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto findById(long id) {
        User user = entityFinder.getUserOrThrow(id);

        return UserMapper.toResponseDto(user);
    }

    @Override
    public UserDto update(UserRequestUpdateDto dto, long id) {
        User req = UserMapper.toEntity(dto, id); // не уверен до конца нужно ли здесь преобразование в доменный объект

        User user = entityFinder.getUserOrThrow(id);

        if (req.getName() != null)
            user.setName(req.getName());

        if (req.getEmail() != null) {
            if (userRepository.existsByEmail(dto.email()))
                throw new DuplicateEmailException();

            user.setEmail(req.getEmail());
        }

        return UserMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public UserDto create(UserRequestCreateDto dto) {
        if (userRepository.existsByEmail(dto.email()))
            throw new DuplicateEmailException();

        User user = UserMapper.toEntity(dto);
        return UserMapper.toResponseDto(userRepository.save(user));
    }

}
