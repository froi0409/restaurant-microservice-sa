package com.froi.restaurant.dish.infrastructure.outputadapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishDbEntityRepository extends JpaRepository<DishDbEntity, String> {
}
