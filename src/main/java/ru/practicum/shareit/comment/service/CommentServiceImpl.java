package ru.practicum.shareit.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.enums.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.comment.dto.CommentRequestCreateDto;
import ru.practicum.shareit.comment.dto.CommentResponseDto;
import ru.practicum.shareit.comment.mapper.CommentMapper;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.comment.repository.CommentRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.web.EntityFinder;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final EntityFinder entityFinder;
    private final BookingRepository bookingRepository;

    @Override
    public CommentResponseDto createComment(long itemId, long userId, CommentRequestCreateDto dto) {
        User user = entityFinder.getUserOrThrow(userId);
        Item item = entityFinder.getItemOrThrow(itemId);

        boolean hasCompletedBooking = bookingRepository
                .existsByItemIdAndBookerIdAndEndAtBeforeAndStatus(
                        itemId,
                        userId,
                        LocalDateTime.now(),
                        BookingStatus.APPROVED
                );

        if (!hasCompletedBooking) {
            throw new RuntimeException("Пользователь не завершил бронирование этого товара");
        }

        Comment comment = new Comment(dto.text(), item, user);

        Comment saved = commentRepository.save(comment);

        return CommentMapper.toDto(saved);
    }
}
