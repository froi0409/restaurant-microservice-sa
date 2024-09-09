package com.froi.restaurant.restaurant.infrastructure.outputports.db;

import com.froi.restaurant.restaurant.domain.Restaurant;

public interface FindRestaurantOutputPort {
    Restaurant findRestaurantById(String restaurantId);
}
