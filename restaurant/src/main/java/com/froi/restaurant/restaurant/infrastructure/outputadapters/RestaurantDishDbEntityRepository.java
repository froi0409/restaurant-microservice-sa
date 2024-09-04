package com.froi.restaurant.restaurant.infrastructure.outputadapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantDishDbEntityRepository extends JpaRepository<RestaurantDishDbEntity, RestaurantDishDbEntityPK> {
}
