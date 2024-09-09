package com.froi.restaurant.order.infrastructure.outputadapters.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailDbEntityRepository extends JpaRepository<OrderDetailDbEntity, String> {
    List<OrderDetailDbEntity> findAllByOrder(String orderId);
}
