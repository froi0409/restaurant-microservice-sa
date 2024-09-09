package com.froi.restaurant.restaurant.infrastructure.inputports.restapi;

import com.froi.restaurant.common.exceptions.DuplicatedEntityException;
import com.froi.restaurant.dish.domain.exceptions.DishException;
import com.froi.restaurant.restaurant.application.adddishtorestaurantusecase.AddDishToRestaurantRequest;

public interface AddDishToRestaurantInputPort {
    void addDishToRestaurantInputPort(AddDishToRestaurantRequest request) throws DishException, DuplicatedEntityException;
}
