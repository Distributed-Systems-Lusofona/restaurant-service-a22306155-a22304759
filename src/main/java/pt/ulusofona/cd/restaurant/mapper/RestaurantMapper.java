package pt.ulusofona.cd.restaurant.mapper;

import org.springframework.stereotype.Component;
import pt.ulusofona.cd.restaurant.dto.RestaurantRequest;
import pt.ulusofona.cd.restaurant.dto.RestaurantResponse;
import pt.ulusofona.cd.restaurant.model.Restaurant;

@Component
public class RestaurantMapper {

    public static RestaurantResponse toResponse(Restaurant entity) {
        if (entity == null) return null;

        return new RestaurantResponse(
                entity.getId(),
                entity.getName(),
                entity.getCity(),
                entity.getCountry(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static Restaurant toEntity(RestaurantRequest request) {
        if (request == null) return null;

        Restaurant r = new Restaurant();
        r.setName(request.getName());
        r.setCity(request.getCity());
        r.setCountry(request.getCountry());
        r.setPhone(request.getPhone());
        r.setEmail(request.getEmail());
        return r;
    }
}
