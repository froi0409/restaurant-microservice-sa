package com.froi.restaurant.dish.application.existsdishusecase;

import com.froi.restaurant.common.UseCase;
import com.froi.restaurant.dish.infrastructure.inputports.restapi.ExistsDishInputPort;
import com.froi.restaurant.dish.infrastructure.outputadapters.DishDbOutputAdapter;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
public class ExistsDishUseCase implements ExistsDishInputPort {

    private DishDbOutputAdapter dishDbOutputAdapter;

    @Autowired
    public ExistsDishUseCase(DishDbOutputAdapter dishDbOutputAdapter) {
        this.dishDbOutputAdapter = dishDbOutputAdapter;
    }

    @Override
    public void existsDish(String dishId) {
        dishDbOutputAdapter.findDishNameById(dishId);
    }
}
