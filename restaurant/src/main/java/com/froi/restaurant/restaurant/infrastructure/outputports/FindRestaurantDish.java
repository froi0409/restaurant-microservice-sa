package com.froi.restaurant.restaurant.infrastructure.outputports;

import com.froi.restaurant.dish.domain.Dish;
import com.froi.restaurant.order.application.makeorderusecase.OrderDish;

public interface FindRestaurantDish {
    Dish findRestaurantDish(String restaurantId, OrderDish orderDish);
}
