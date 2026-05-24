package ru.practicum.shareit.booking.service;


import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.request.BookingRequestCreateDto;
import ru.practicum.shareit.booking.dto.response.BookingResponseDto;
import ru.practicum.shareit.booking.enums.BookingStatus;
import ru.practicum.shareit.booking.enums.BookingStatusRequest;
import ru.practicum.shareit.booking.exception.BookingEndMustBeAfterStartException;
import ru.practicum.shareit.booking.exception.ItemUnavailableException;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.specification.BookingSpecification;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.exception.InvalidBookerIdException;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.web.EntityFinder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final EntityFinder entityFinder;

    @Override
    public BookingResponseDto createBooking(BookingRequestCreateDto bookingDto, long bookerId) {
        validateBookingDates(bookingDto);

        User booker = entityFinder.getUserOrThrow(bookerId);

        Item item = itemRepository.findById(bookingDto.itemId())
                .orElseThrow(() -> new ItemNotFoundException(bookingDto.itemId()));

        if (!item.getAvailable())
            throw new ItemUnavailableException(item.getId());

        Booking booking = BookingMapper.toEntity(bookingDto, item, booker);

        return BookingMapper.toResponseDto(bookingRepository.save(booking));
    }

    @Override
    public BookingResponseDto updateBooking(long userId, boolean approved, long bookingId) {

        Booking booking = entityFinder.getBookingOrThrow(bookingId);
        checkUserExists(userId);

        if (booking.getStatus() != BookingStatus.WAITING)
            throw new ValidationException("Бронирование уже было обработано");

        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);

        return BookingMapper.toResponseDto(bookingRepository.save(booking));
    }

    @Override
    public BookingResponseDto getBooking(long userId, long bookingId) {
        Booking booking = entityFinder.getBookingOrThrow(bookingId);
        checkUserExists(userId);


        return BookingMapper.toResponseDto(booking);
    }

    @Override
    public List<BookingResponseDto> getBookingOwner(long userId, BookingStatusRequest status) {
        checkUserExists(userId);

        Specification<Booking> specification = Specification
                .where(BookingSpecification.fetchItemBookerAndOwner())
                .and(BookingSpecification.byOwnerId(userId));

        if (status != BookingStatusRequest.ALL)
            specification = specification.and(BookingSpecification.hasStatus(status));


        return bookingRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "startAt"))
                .stream()
                .map(BookingMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<BookingResponseDto> getBookingBooker(long userId, BookingStatusRequest status) {
        checkUserExists(userId);

        Specification<Booking> specification = Specification
                .where(BookingSpecification.fetchItemBookerAndOwner())
                .and(BookingSpecification.byBookerId(userId));

        if (status != BookingStatusRequest.ALL)
            specification = specification.and(BookingSpecification.hasStatus(status));


        return bookingRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "startAt"))
                .stream()
                .map(BookingMapper::toResponseDto)
                .toList();
    }

    // Вспомогательные методы
    private void validateBookingDates(BookingRequestCreateDto bookingDto) {
        if (!bookingDto.start().isBefore(bookingDto.end())) {
            throw new BookingEndMustBeAfterStartException();
        }
    }

    private void checkUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new InvalidBookerIdException(userId);
        }
    }
}
