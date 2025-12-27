package pt.ulusofona.cd.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.restaurant.dto.RestaurantRequest;
import pt.ulusofona.cd.restaurant.exception.RestaurantNotFoundException;
import pt.ulusofona.cd.restaurant.mapper.RestaurantMapper;
import pt.ulusofona.cd.restaurant.model.Restaurant;
import pt.ulusofona.cd.restaurant.repository.RestaurantRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Restaurant createRestaurant(RestaurantRequest request) {
        Restaurant entity = RestaurantMapper.toEntity(request);
        return restaurantRepository.save(entity);
    }

    public Restaurant getRestaurantsById(UUID id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found: " + id));
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant updateRestaurant(UUID id, RestaurantRequest request) {
        Restaurant restaurant = getRestaurantsById(id);

        restaurant.setName(request.getName());
        restaurant.setCity(request.getCity());
        restaurant.setCountry(request.getCountry());
        restaurant.setPhone(request.getPhone());
        restaurant.setEmail(request.getEmail());

        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(UUID id) {
        Restaurant restaurant = getRestaurantsById(id);
        restaurantRepository.delete(restaurant);
    }
}
