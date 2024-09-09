package com.froi.restaurant.order.infrastructure.outputadapters.db;

import com.froi.restaurant.order.application.findorderusecase.OrderCostsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDbEntityRepository extends JpaRepository<OrderDbEntity, String> {
    @Query(value = "SELECT r.id FROM restaurant r LEFT JOIN \"order\" o ON r.id = o.restaurant WHERE o.id = :orderId", nativeQuery = true)
    String findRestaurantIdByOrderId(@Param("orderId") String orderId);

    @Query("SELECT new com.froi.restaurant.order.application.findorderusecase.OrderCostsInfo(o.id, r.id, r.name, o.orderName, d.id, d.name, od.dishCost, o.date) " +
            "FROM OrderDetailDbEntity od " +
            "JOIN OrderDbEntity o ON od.order = o.id " +
            "JOIN RestaurantDbEntity r ON o.restaurant = r.id " +
            "JOIN DishDbEntity d ON od.dish = d.id")
    List<OrderCostsInfo> findOrderDetails();

}
