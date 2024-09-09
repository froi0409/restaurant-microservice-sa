package com.froi.restaurant.order.application.makeorderusecase;

import com.froi.restaurant.order.domain.Order;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class MakeOrderRequest {
    String restaurantId;
    String orderName;
    List<OrderDish> orderDishes;

    public Order toDomain() {
        return Order.builder()
                .orderName(orderName)
                .build();
    }
}
