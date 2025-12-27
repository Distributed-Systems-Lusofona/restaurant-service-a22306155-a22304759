package pt.ulusofona.cd.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.restaurant.dto.MenuItemRequest;
import pt.ulusofona.cd.restaurant.exception.MenuItemNotFoundException;
import pt.ulusofona.cd.restaurant.mapper.MenuItemMapper;
import pt.ulusofona.cd.restaurant.model.MenuItem;
import pt.ulusofona.cd.restaurant.model.Restaurant;
import pt.ulusofona.cd.restaurant.repository.MenuItemRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantService restaurantService;

    public MenuItem createMenuItem(UUID restaurantId, MenuItemRequest request) {
        Restaurant restaurant = restaurantService.getRestaurantsById(restaurantId);
        MenuItem entity = MenuItemMapper.toEntity(request, restaurant);

        return menuItemRepository.save(entity);
    }

    public List<MenuItem> getMenuItemsByRestaurant(UUID restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    public MenuItem updateMenuItem(
            UUID restaurantId,
            UUID menuItemId,
            MenuItemRequest request
    ) {
        MenuItem menuItem = menuItemRepository
                .findByIdAndRestaurantId(menuItemId, restaurantId)
                .orElseThrow(() ->
                        new MenuItemNotFoundException(
                                "Menu item not found for this restaurant"
                        )
                );

        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setCurrency(request.getCurrency());

        return menuItemRepository.save(menuItem);
    }

    public void deleteMenuItem(UUID restaurantId, UUID menuItemId) {
        MenuItem menuItem = menuItemRepository
                .findByIdAndRestaurantId(menuItemId, restaurantId)
                .orElseThrow(() ->
                        new MenuItemNotFoundException(
                                "Menu item not found for this restaurant"
                        )
                );

        menuItemRepository.delete(menuItem);
    }
}
