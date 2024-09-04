package com.froi.restaurant.order.infrastructure.outputadapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDbEntityRepository extends JpaRepository<OrderDbEntity, String> {
}
