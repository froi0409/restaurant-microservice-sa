package com.froi.restaurant.restaurant.application.adddishtorestaurantusecase;

import com.froi.restaurant.restaurant.domain.RestaurantDish;
import lombok.Value;

@Value
public class AddDishToRestaurantRequest {
    String restaurant;
    String dish;
}
