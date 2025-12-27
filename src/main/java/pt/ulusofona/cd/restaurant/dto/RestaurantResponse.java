package pt.ulusofona.cd.restaurant.dto;

import lombok.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponse {
    private UUID id;
    private String name;
    private String city;
    private String country;
    private String phone;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;
}

