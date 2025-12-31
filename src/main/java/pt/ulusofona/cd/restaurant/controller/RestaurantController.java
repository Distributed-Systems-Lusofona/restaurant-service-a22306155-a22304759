package pt.ulusofona.cd.restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.restaurant.dto.RestaurantRequest;
import pt.ulusofona.cd.restaurant.dto.RestaurantResponse;
import pt.ulusofona.cd.restaurant.mapper.RestaurantMapper;
import pt.ulusofona.cd.restaurant.model.Restaurant;
import pt.ulusofona.cd.restaurant.service.RestaurantService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantResponse> create(@Valid @RequestBody RestaurantRequest request) {
        Restaurant created = restaurantService.createRestaurant(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RestaurantMapper.toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAll(@RequestParam(required = false) String ids) {

        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        List<RestaurantResponse> responseList = restaurants.stream()
                .map(RestaurantMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> getById(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(RestaurantMapper.toResponse(restaurantService.getRestaurantsById(restaurantId)));
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> update(@PathVariable UUID restaurantId, @Valid @RequestBody RestaurantRequest request) {
        return ResponseEntity.ok(RestaurantMapper.toResponse(restaurantService.updateRestaurant(restaurantId, request)));
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Void> delete(@PathVariable UUID restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.noContent().build();
    }
}
