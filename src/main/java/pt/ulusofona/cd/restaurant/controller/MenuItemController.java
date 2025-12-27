package pt.ulusofona.cd.restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.restaurant.dto.MenuItemRequest;
import pt.ulusofona.cd.restaurant.dto.MenuItemResponse;
import pt.ulusofona.cd.restaurant.mapper.MenuItemMapper;
import pt.ulusofona.cd.restaurant.model.MenuItem;
import pt.ulusofona.cd.restaurant.service.MenuItemService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemResponse> create(
            @PathVariable UUID restaurantId,
            @Valid @RequestBody MenuItemRequest request
    ) {
        MenuItem created = menuItemService.createMenuItem(restaurantId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MenuItemMapper.toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponse>> getAllByRestaurant(
            @PathVariable UUID restaurantId
    ) {
        List<MenuItem> items =
                menuItemService.getMenuItemsByRestaurant(restaurantId);

        return ResponseEntity.ok(
                items.stream()
                        .map(MenuItemMapper::toResponse)
                        .toList()
        );
    }

    @PutMapping("/{menuItemId}")
    public ResponseEntity<MenuItemResponse> update(
            @PathVariable UUID restaurantId,
            @PathVariable UUID menuItemId,
            @Valid @RequestBody MenuItemRequest request
    ) {
        MenuItem updated = menuItemService.updateMenuItem(restaurantId, menuItemId, request);

        return ResponseEntity.ok(MenuItemMapper.toResponse(updated));
    }

    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID restaurantId,
            @PathVariable UUID menuItemId
    ) {
        menuItemService.deleteMenuItem(restaurantId, menuItemId);
        return ResponseEntity.noContent().build();
    }
}
