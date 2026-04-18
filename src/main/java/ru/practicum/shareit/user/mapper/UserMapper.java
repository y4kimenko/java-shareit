package ru.practicum.shareit.user.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.request.UserRequestCreateDto;
import ru.practicum.shareit.user.dto.request.UserRequestData;
import ru.practicum.shareit.user.dto.request.UserRequestUpdateDto;

@Component
public class UserMapper {
    public static UserDto toResponseDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User toEntity(UserRequestUpdateDto dto, long id) {
        if (dto == null) return null;

        User user = new User();

        user.setId(id);
        applyToEntity(dto, user);

        return user;
    }

    public static User toEntity(UserRequestCreateDto dto) {
        if (dto == null) return null;

        User user = new User();

        applyToEntity(dto, user);

        return user;
    }

    public static void applyToEntity(UserRequestData dto, User target) {
        if (dto == null || target == null) return;

        target.setName(dto.name());
        target.setEmail(dto.email());

    }
}
