package com.froi.restaurant.dish.infrastructure.inputports.restapi;

import com.froi.restaurant.dish.application.createdishusecase.CreateDishRequest;
import com.froi.restaurant.dish.domain.exceptions.DishException;

public interface CreateDishInputPort {
    void createDish(CreateDishRequest createDishRequest) throws DishException;
}
