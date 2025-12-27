// java
package pt.ulusofona.cd.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.restaurant.dto.AvailabilitySlotRequest;
import pt.ulusofona.cd.restaurant.exception.AvailabilitySlotNotFoundException;
import pt.ulusofona.cd.restaurant.mapper.AvailabilitySlotMapper;
import pt.ulusofona.cd.restaurant.model.AvailabilitySlot;
import pt.ulusofona.cd.restaurant.model.Restaurant;
import pt.ulusofona.cd.restaurant.repository.AvailabilitySlotRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AvailabilitySlotService {

    private final AvailabilitySlotRepository availabilitySlotRepository;
    private final RestaurantService restaurantService;

    public AvailabilitySlot createSlot(UUID restaurantId, AvailabilitySlotRequest request) {
        Restaurant restaurant = restaurantService.getRestaurantsById(restaurantId);
        AvailabilitySlot slot = AvailabilitySlotMapper.toEntity(request, restaurant);
        return availabilitySlotRepository.save(slot);
    }

    public List<AvailabilitySlot> getSlotsByRestaurant(UUID restaurantId) {
        return availabilitySlotRepository.findByRestaurantId(restaurantId);
    }

    public List<AvailabilitySlot> getSlotsByRestaurantAndDate(UUID restaurantId, LocalDate date) {
        return availabilitySlotRepository.findByRestaurantIdAndDate(restaurantId, date);
    }

    public List<AvailabilitySlot> getSlotsByRestaurantAndDateTime(UUID restaurantId, LocalDate date, LocalTime time) {
        return availabilitySlotRepository
                .findByRestaurantIdAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                        restaurantId, date, time, time
                );
    }

    public AvailabilitySlot getSlotById(UUID restaurantId, UUID slotId) {
        return availabilitySlotRepository
                .findByIdAndRestaurantId(slotId, restaurantId)
                .orElseThrow(() -> new AvailabilitySlotNotFoundException("Slot não encontrado para este restaurante: " + slotId));
    }

    public AvailabilitySlot updateSlot(UUID restaurantId, UUID slotId, AvailabilitySlotRequest request) {
        AvailabilitySlot slot = availabilitySlotRepository
                .findByIdAndRestaurantId(slotId, restaurantId)
                .orElseThrow(() -> new AvailabilitySlotNotFoundException("Slot não encontrado para este restaurante: " + slotId));

        slot.setDate(request.getDate());
        slot.setStartTime(request.getStartTime());
        slot.setEndTime(request.getEndTime());
        slot.setCapacity(request.getCapacity());
        slot.setSeatsAvailable(request.getSeatsAvailable());

        return availabilitySlotRepository.save(slot);
    }

    public void deleteSlot(UUID restaurantId, UUID slotId) {
        AvailabilitySlot slot = availabilitySlotRepository
                .findByIdAndRestaurantId(slotId, restaurantId)
                .orElseThrow(() -> new AvailabilitySlotNotFoundException("Slot não encontrado para este restaurante: " + slotId));

        availabilitySlotRepository.delete(slot);
    }
}