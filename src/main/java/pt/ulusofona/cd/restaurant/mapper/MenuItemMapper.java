package pt.ulusofona.cd.restaurant.mapper;

import org.springframework.stereotype.Component;
import pt.ulusofona.cd.restaurant.dto.MenuItemRequest;
import pt.ulusofona.cd.restaurant.dto.MenuItemResponse;
import pt.ulusofona.cd.restaurant.model.MenuItem;
import pt.ulusofona.cd.restaurant.model.Restaurant;

import java.util.UUID;

@Component
public class MenuItemMapper {

    public static MenuItemResponse toResponse(MenuItem entity) {
        if (entity == null) return null;
        UUID restaurantId = entity.getRestaurant() != null ? entity.getRestaurant().getId() : null;

        return new MenuItemResponse(
            entity.getId(),
            restaurantId,
            entity.getName(),
            entity.getDescription(),
            entity.getPrice(),
            entity.getCurrency()
        );
    }

    public static MenuItem toEntity(MenuItemRequest request, Restaurant restaurant) {
        if (request == null) return null;

        MenuItem m = new MenuItem();
        m.setRestaurant(restaurant);
        m.setName(request.getName());
        m.setDescription(request.getDescription());
        m.setPrice(request.getPrice());
        m.setCurrency(request.getCurrency());
        return m;
    }
}
