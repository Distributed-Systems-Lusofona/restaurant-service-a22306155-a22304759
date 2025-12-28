package pt.ulusofona.cd.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ulusofona.cd.restaurant.model.AvailabilitySlot;
import pt.ulusofona.cd.restaurant.model.MenuItem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, UUID> {
    Optional<AvailabilitySlot> findByIdAndRestaurantId(UUID id, UUID restaurantId);

    List<AvailabilitySlot> findByRestaurantId(UUID restaurantId);

    List<AvailabilitySlot> findByRestaurantIdAndSlotDate(UUID restaurantId, LocalDate slotDate);

    List<AvailabilitySlot> findByRestaurantIdAndSlotDateAfterAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            UUID restaurantId,
            LocalDate slotDate,
            LocalTime startTime,
            LocalTime endTime
    );
}

