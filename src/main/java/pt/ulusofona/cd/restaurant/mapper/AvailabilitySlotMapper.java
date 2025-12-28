package pt.ulusofona.cd.restaurant.mapper;

import org.springframework.stereotype.Component;
import pt.ulusofona.cd.restaurant.dto.AvailabilitySlotRequest;
import pt.ulusofona.cd.restaurant.dto.AvailabilitySlotResponse;
import pt.ulusofona.cd.restaurant.model.AvailabilitySlot;
import pt.ulusofona.cd.restaurant.model.Restaurant;

import java.util.UUID;

@Component
public class AvailabilitySlotMapper {

    public static AvailabilitySlotResponse toResponse(AvailabilitySlot entity) {
        if (entity == null) return null;

        UUID restaurantId = entity.getRestaurant() != null ? entity.getRestaurant().getId() : null;
        return new AvailabilitySlotResponse(
                entity.getId(),
                restaurantId,
                entity.getSlotDate(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getCapacity(),
                entity.getSeatsAvailable()
        );
    }

    public static AvailabilitySlot toEntity(AvailabilitySlotRequest request, Restaurant restaurant) {
        if (request == null) return null;

        AvailabilitySlot a = new AvailabilitySlot();
        a.setRestaurant(restaurant);
        a.setSlotDate(request.getSlotDate());
        a.setStartTime(request.getStartTime());
        a.setEndTime(request.getEndTime());
        a.setCapacity(request.getCapacity());
        a.setSeatsAvailable(request.getSeatsAvailable());
        return a;
    }

}

