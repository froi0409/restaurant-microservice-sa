package com.froi.restaurant.restaurant.infrastructure.outputadapters;

import com.froi.restaurant.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantDbEntityRepository extends JpaRepository<RestaurantDbEntity, String> {
}
