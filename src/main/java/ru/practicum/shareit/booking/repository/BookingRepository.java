package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.dto.response.BookingShortResponseDto;
import ru.practicum.shareit.booking.enums.BookingStatus;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.projection.BookingShortProjection;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    boolean existsByItemIdAndBookerIdAndEndAtBeforeAndStatus(
            Long itemId,
            Long bookerId,
            LocalDateTime now,
            BookingStatus status
    );

    @Query("""
             SELECT new ru.practicum.shareit.booking.repository.projection.BookingShortProjection(
                          b.item.id,
                          b.id,
                          b.booker.id,
                          b.startAt
                      )
                      FROM Booking b
                      WHERE b.item.id IN :itemIds
                        AND (
                            b.endAt = (
                                SELECT MAX(lastBooking.endAt)
                                FROM Booking lastBooking
                                WHERE lastBooking.item.id = b.item.id
                                  AND lastBooking.endAt < :now
                            )
                            OR
                            b.startAt = (
                                SELECT MIN(nextBooking.startAt)
                                FROM Booking nextBooking
                                WHERE nextBooking.item.id = b.item.id
                                  AND nextBooking.startAt > :now
                            )
                        )
                      ORDER BY b.item.id ASC, b.startAt ASC
            """)
    List<BookingShortProjection> findLastAndNextBookingsByItemIds(
            @Param("itemIds") Collection<Long> itemIds,
            @Param("now") LocalDateTime now
    );


    default Map<Long, List<BookingShortResponseDto>> findLastAndNextBookingsMapByItemIds(
            Collection<Long> itemIds,
            LocalDateTime now
    ) {
        return findLastAndNextBookingsByItemIds(itemIds, now)
                .stream()
                .sorted(Comparator.comparing(BookingShortProjection::itemId)
                        .thenComparing(BookingShortProjection::startAt))
                .collect(Collectors.groupingBy(
                        BookingShortProjection::itemId,
                        Collectors.mapping(
                                booking -> new BookingShortResponseDto(
                                        booking.bookingId(),
                                        booking.bookerId()
                                ),
                                Collectors.toList()
                        )
                ));
    }
}
