package pt.ulusofona.cd.restaurant.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilitySlotRequest {
    @NotNull
    private UUID restaurantId;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    @Min(0)
    private int capacity;

    @NotNull
    @Min(0)
    private int seatsAvailable;
}

