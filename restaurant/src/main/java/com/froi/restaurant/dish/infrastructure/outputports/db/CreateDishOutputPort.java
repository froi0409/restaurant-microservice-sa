package com.froi.restaurant.dish.infrastructure.outputports.db;

import com.froi.restaurant.dish.domain.Dish;

public interface CreateDishOutputPort {
    void createDish(Dish dish);

}
