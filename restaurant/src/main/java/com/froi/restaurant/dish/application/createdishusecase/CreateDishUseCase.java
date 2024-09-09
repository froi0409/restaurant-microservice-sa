package com.froi.restaurant.dish.application.createdishusecase;

import com.froi.restaurant.common.UseCase;
import com.froi.restaurant.dish.domain.Dish;
import com.froi.restaurant.dish.domain.exceptions.DishException;
import com.froi.restaurant.dish.infrastructure.inputports.restapi.CreateDishInputPort;
import com.froi.restaurant.dish.infrastructure.outputadapters.DishDbOutputAdapter;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
public class CreateDishUseCase implements CreateDishInputPort {

    private DishDbOutputAdapter dishDbOutputAdapter;

    @Autowired
    public CreateDishUseCase(DishDbOutputAdapter dishDbOutputAdapter) {
        this.dishDbOutputAdapter = dishDbOutputAdapter;
    }

    @Override
    public void createDish(CreateDishRequest createDishRequest) throws DishException {
        Dish dish = createDishRequest.toDomain();
        dish.validate();
        dishDbOutputAdapter.createDish(dish);
    }
}
