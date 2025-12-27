package pt.ulusofona.cd.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ulusofona.cd.restaurant.model.MenuItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {
    Optional<MenuItem> findByIdAndRestaurantId(UUID id, UUID restaurantId);
    List<MenuItem> findByRestaurantId(UUID restaurantId);
}

