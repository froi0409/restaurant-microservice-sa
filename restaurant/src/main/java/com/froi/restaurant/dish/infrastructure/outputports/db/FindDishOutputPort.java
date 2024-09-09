package com.froi.restaurant.dish.infrastructure.outputports.db;

import com.froi.restaurant.dish.domain.Dish;
import com.froi.restaurant.restaurant.domain.RestaurantDish;

import java.util.Optional;

public interface FindDishOutputPort {
    String findDishNameById(String dishId);

    Dish findDishById(String id);

    Optional<RestaurantDish> findRestaurantDish(String restaurantId, String dishId);
}
