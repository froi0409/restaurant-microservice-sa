package com.froi.restaurant.restaurant.infrastructure.outputports;

import com.froi.restaurant.restaurant.domain.Restaurant;

public interface FindRestaurantOutputPort {
    Restaurant findRestaurantById(String restaurantId);
}
