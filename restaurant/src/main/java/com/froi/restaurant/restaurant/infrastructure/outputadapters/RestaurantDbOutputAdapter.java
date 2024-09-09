package com.froi.restaurant.restaurant.infrastructure.outputadapters;

import com.froi.restaurant.common.PersistenceAdapter;
import com.froi.restaurant.dish.domain.Dish;
import com.froi.restaurant.dish.infrastructure.outputadapters.DishDbEntity;
import com.froi.restaurant.dish.infrastructure.outputadapters.DishDbEntityRepository;
import com.froi.restaurant.order.application.makeorderusecase.OrderDish;
import com.froi.restaurant.restaurant.domain.Restaurant;
import com.froi.restaurant.restaurant.infrastructure.outputports.db.CreateRestaurantOutputPort;
import com.froi.restaurant.restaurant.infrastructure.outputports.db.FindRestaurantDish;
import com.froi.restaurant.restaurant.infrastructure.outputports.db.FindRestaurantOutputPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@PersistenceAdapter
@Transactional
public class RestaurantDbOutputAdapter implements FindRestaurantOutputPort, FindRestaurantDish, CreateRestaurantOutputPort {

    private RestaurantDbEntityRepository restaurantDbEntityRepository;
    private RestaurantDishDbEntityRepository restaurantDishDbEntityRepository;

    private DishDbEntityRepository dishDbEntityRepository;

    @Autowired
    public RestaurantDbOutputAdapter(RestaurantDbEntityRepository restaurantDbEntityRepository, RestaurantDishDbEntityRepository restaurantDishDbEntityRepository, DishDbEntityRepository dishDbEntityRepository) {
        this.restaurantDbEntityRepository = restaurantDbEntityRepository;
        this.restaurantDishDbEntityRepository = restaurantDishDbEntityRepository;
        this.dishDbEntityRepository = dishDbEntityRepository;
    }

    @Override
    public Restaurant findRestaurantById(String restaurantId) {
        return restaurantDbEntityRepository.findById(restaurantId)
                .map(RestaurantDbEntity::toDomain)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Restaurant with id %s not found", restaurantId)));
    }


    @Override
    public Dish findRestaurantDish(String restaurantId, OrderDish orderDish) {
        RestaurantDishDbEntity restaurantDishDb = restaurantDishDbEntityRepository
                .findById(new RestaurantDishDbEntityPK(restaurantId, orderDish.getDishId()))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Dish with id %s not found in restaurant with id %s", orderDish.getDishId(), restaurantId)));

        Dish dish = dishDbEntityRepository.findById(orderDish.getDishId())
                .map(DishDbEntity::toDomain)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Dish with id %s not found", orderDish.getDishId())));
        dish.setNote(orderDish.getDescription());
        return dish;
    }

    @Override
    public void createRestaurant(Restaurant restaurant) {
        RestaurantDbEntity restaurantDbEntity = RestaurantDbEntity.fromDomain(restaurant);
        restaurantDbEntityRepository.save(restaurantDbEntity);
    }
}
