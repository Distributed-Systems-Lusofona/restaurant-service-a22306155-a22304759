package pt.ulusofona.cd.restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.restaurant.dto.AvailabilitySlotRequest;
import pt.ulusofona.cd.restaurant.dto.AvailabilitySlotResponse;
import pt.ulusofona.cd.restaurant.dto.SeatsDto;
import pt.ulusofona.cd.restaurant.mapper.AvailabilitySlotMapper;
import pt.ulusofona.cd.restaurant.model.AvailabilitySlot;
import pt.ulusofona.cd.restaurant.service.AvailabilitySlotService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/availability")
@RequiredArgsConstructor
public class AvailabilitySlotController {

    private final AvailabilitySlotService availabilitySlotService;

    @PostMapping
    public ResponseEntity<AvailabilitySlotResponse> create(
            @PathVariable UUID restaurantId,
            @Valid @RequestBody AvailabilitySlotRequest request
    ) {
        AvailabilitySlot created = availabilitySlotService.createSlot(restaurantId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AvailabilitySlotMapper.toResponse(created));
    }

    // Reserva lugares da slot
    @PostMapping("/reserve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reserveSeats(
            @PathVariable UUID restaurantId,
            @Valid @RequestBody SeatsDto request
    ) {
        availabilitySlotService.reserveSeats(
                restaurantId,
                request.getAvailabilitySlotId(),
                request.getSeats()
        );
    }

    // Devolve lugares da slot que foi tirada
    @PostMapping("/release")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void releaseSeats(
            @PathVariable UUID restaurantId,
            @Valid @RequestBody SeatsDto request
    ) {
        availabilitySlotService.releaseSeats(
                restaurantId,
                request.getAvailabilitySlotId(),
                request.getSeats()
        );
    }

    @GetMapping
    public ResponseEntity<List<AvailabilitySlotResponse>> getSlotsByRestaurant(
            @PathVariable UUID restaurantId,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) LocalTime time,
            @RequestParam(required = false) int partySize
    ) {

        List<AvailabilitySlot> slotsEntity;

        if (date != null && time != null) {
            slotsEntity = availabilitySlotService.getSlotsByRestaurantAndDateTime(restaurantId, date, time);
        } else if (date != null) {
            slotsEntity = availabilitySlotService.getSlotsByRestaurantAndDate(restaurantId, date);
        } else {
            slotsEntity = availabilitySlotService.getSlotsByRestaurant(restaurantId);
        }

        if (partySize > 0) {
            slotsEntity = slotsEntity.stream()
                    .filter(slot -> slot.getSeatsAvailable() >= partySize)
                    .toList();
        }

        List<AvailabilitySlotResponse> slots = slotsEntity.stream()
                .map(AvailabilitySlotMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(slots);
    }

    @GetMapping("/{slotId}")
    public ResponseEntity<AvailabilitySlotResponse> get(
            @PathVariable UUID restaurantId,
            @PathVariable UUID slotId
    ) {
        AvailabilitySlot slot = availabilitySlotService.getSlotById(restaurantId, slotId);
        return ResponseEntity.ok(AvailabilitySlotMapper.toResponse(slot));
    }

    @PutMapping("/{slotId}")
    public ResponseEntity<AvailabilitySlotResponse> update(
            @PathVariable UUID restaurantId,
            @PathVariable UUID slotId,
            @Valid @RequestBody AvailabilitySlotRequest request
    ) {
        AvailabilitySlot updated = availabilitySlotService.updateSlot(restaurantId, slotId, request);
        return ResponseEntity.ok(AvailabilitySlotMapper.toResponse(updated));
    }

    @DeleteMapping("/{slotId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID restaurantId,
            @PathVariable UUID slotId
    ) {
        availabilitySlotService.deleteSlot(restaurantId, slotId);
        return ResponseEntity.noContent().build();
    }
}