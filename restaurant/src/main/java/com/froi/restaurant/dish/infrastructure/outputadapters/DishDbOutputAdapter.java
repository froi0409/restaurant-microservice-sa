package com.froi.restaurant.dish.infrastructure.outputadapters;

import com.froi.restaurant.common.PersistenceAdapter;
import com.froi.restaurant.dish.domain.Dish;
import com.froi.restaurant.dish.infrastructure.outputports.db.CreateDishOutputPort;
import com.froi.restaurant.dish.infrastructure.outputports.db.FindDishOutputPort;
import com.froi.restaurant.restaurant.domain.RestaurantDish;
import com.froi.restaurant.restaurant.infrastructure.outputadapters.RestaurantDbEntity;
import com.froi.restaurant.restaurant.infrastructure.outputadapters.RestaurantDishDbEntity;
import com.froi.restaurant.restaurant.infrastructure.outputadapters.RestaurantDishDbEntityPK;
import com.froi.restaurant.restaurant.infrastructure.outputadapters.RestaurantDishDbEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@PersistenceAdapter
public class DishDbOutputAdapter implements FindDishOutputPort, CreateDishOutputPort {

    private DishDbEntityRepository dishDbEntityRepository;
    private RestaurantDishDbEntityRepository restaurantDishDbEntityRepository;

    @Autowired
    public DishDbOutputAdapter(DishDbEntityRepository dishDbEntityRepository, RestaurantDishDbEntityRepository restaurantDishDbEntityRepository) {
        this.dishDbEntityRepository = dishDbEntityRepository;
        this.restaurantDishDbEntityRepository = restaurantDishDbEntityRepository;
    }

    @Override
    public String findDishNameById(String dishId) {
        DishDbEntity dishDbEntity = dishDbEntityRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Dish with id %s not found", dishId)));
        return dishDbEntity.getName();
    }

    @Override
    public Dish findDishById(String id) {
       return dishDbEntityRepository.findById(id)
               .map(DishDbEntity::toDomain)
               .orElseThrow(() -> new EntityNotFoundException(String.format("Dish with id %s not found", id)));
    }

    @Override
    public Optional<RestaurantDish> findRestaurantDish(String restaurantId, String dishId) {
        return restaurantDishDbEntityRepository.findById(new RestaurantDishDbEntityPK(restaurantId, dishId))
                .map(RestaurantDishDbEntity::toDomain);

    }

    @Override
    public void createDish(Dish dish) {
        DishDbEntity dishDbEntity = DishDbEntity.fromDomain(dish);
        dishDbEntityRepository.save(dishDbEntity);
    }

    @Override
    public void createRestaurantDish(RestaurantDish restaurantDish) {
        RestaurantDishDbEntity restaurantDishDbEntity = RestaurantDishDbEntity.fromDomain(restaurantDish);
        restaurantDishDbEntityRepository.save(restaurantDishDbEntity);
    }
}
