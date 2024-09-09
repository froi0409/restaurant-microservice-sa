package com.froi.restaurant.dish.infrastructure.outputports.db;

import com.froi.restaurant.dish.domain.Dish;
import com.froi.restaurant.restaurant.domain.RestaurantDish;

public interface CreateDishOutputPort {
    void createDish(Dish dish);
    void createRestaurantDish(RestaurantDish restaurantDish);
}
