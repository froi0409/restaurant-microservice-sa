package com.froi.restaurant.dish.infrastructure.outputadapters;

import com.froi.restaurant.common.PersistenceAdapter;
import com.froi.restaurant.dish.infrastructure.outputports.db.FindDishOutputPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

@PersistenceAdapter
public class DishDbOutputAdapter implements FindDishOutputPort {

    private DishDbEntityRepository dishDbEntityRepository;

    @Autowired
    public DishDbOutputAdapter(DishDbEntityRepository dishDbEntityRepository) {
        this.dishDbEntityRepository = dishDbEntityRepository;
    }

    @Override
    public String findDishNameById(String dishId) {
        DishDbEntity dishDbEntity = dishDbEntityRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Dish with id %s not found", dishId)));
        return dishDbEntity.getName();
    }
}
