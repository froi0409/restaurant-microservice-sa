package com.froi.restaurant.order.infrastructure.outputadapters.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDbEntityRepository extends JpaRepository<OrderDbEntity, String> {
    @Query(value = "SELECT r.id FROM restaurant r LEFT JOIN \"order\" o ON r.id = o.restaurant WHERE o.id = :orderId", nativeQuery = true)
    String findRestaurantIdByOrderId(@Param("orderId") String orderId);

}
