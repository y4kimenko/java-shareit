package ru.practicum.shareit.booking.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.shareit.booking.enums.BookingStatusRequest;
import ru.practicum.shareit.booking.model.Booking;

public class BookingSpecification {
    public static Specification<Booking> fetchItemBookerAndOwner() {
        return (root, query, criteriaBuilder) -> {
            if (query.getResultType() != Long.class) {
                root.fetch("item").fetch("owner");
                root.fetch("booker");
                query.distinct(true);
            }

            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Booking> byBookerId(Long bookerId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("booker").get("id"), bookerId);
    }

    public static Specification<Booking> byOwnerId(Long ownerId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("item").get("owner").get("id"), ownerId);
    }

    public static Specification<Booking> hasStatus(BookingStatusRequest status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }
}
