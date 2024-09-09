package com.froi.restaurant.restaurant.application.adddishtorestaurantusecase;

import com.froi.restaurant.common.UseCase;
import com.froi.restaurant.common.exceptions.DuplicatedEntityException;
import com.froi.restaurant.dish.domain.Dish;
import com.froi.restaurant.dish.domain.exceptions.DishException;
import com.froi.restaurant.dish.infrastructure.outputadapters.DishDbOutputAdapter;
import com.froi.restaurant.restaurant.domain.Restaurant;
import com.froi.restaurant.restaurant.domain.RestaurantDish;
import com.froi.restaurant.restaurant.infrastructure.inputports.restapi.AddDishToRestaurantInputPort;
import com.froi.restaurant.restaurant.infrastructure.outputadapters.RestaurantDbOutputAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@UseCase
public class AddDishToRestaurantUseCase implements AddDishToRestaurantInputPort {

    private DishDbOutputAdapter dishDbOutputAdapter;
    private RestaurantDbOutputAdapter restaurantDbOutputAdapter;

    @Autowired
    public AddDishToRestaurantUseCase(DishDbOutputAdapter dishDbOutputAdapter, RestaurantDbOutputAdapter restaurantDbOutputAdapter) {
        this.dishDbOutputAdapter = dishDbOutputAdapter;
        this.restaurantDbOutputAdapter = restaurantDbOutputAdapter;
    }

    @Override
    public void addDishToRestaurantInputPort(AddDishToRestaurantRequest request) throws DishException, DuplicatedEntityException {
        Restaurant restaurant = restaurantDbOutputAdapter.findRestaurantById(request.getRestaurant());
        Dish dish = dishDbOutputAdapter.findDishById(request.getDish());
        Optional<RestaurantDish> restaurantDishOp = dishDbOutputAdapter.findRestaurantDish(request.getRestaurant(), request.getDish());
        if (restaurantDishOp.isPresent()) {
            throw new DuplicatedEntityException(String.format("Dish with id %s already exists in restaurant with id %s", request.getDish(), request.getRestaurant()));
        }

        RestaurantDish restaurantDish = RestaurantDish.builder()
                .restaurant(restaurant)
                .dish(dish)
                .isEnable(true)
                .build();
        restaurantDish.validate();
        dishDbOutputAdapter.createRestaurantDish(restaurantDish);
    }
}
