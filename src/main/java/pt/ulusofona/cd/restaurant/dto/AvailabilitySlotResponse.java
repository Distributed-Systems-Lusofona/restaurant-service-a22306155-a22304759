package pt.ulusofona.cd.restaurant.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilitySlotResponse {
    private UUID id;
    private UUID restaurantId;
    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int capacity;
    private int seatsAvailable;
}

