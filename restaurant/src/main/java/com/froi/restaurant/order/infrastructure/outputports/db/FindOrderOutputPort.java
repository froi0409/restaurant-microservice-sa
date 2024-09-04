package com.froi.restaurant.order.infrastructure.outputports.db;

import com.froi.restaurant.order.domain.Order;

public interface FindOrderOutputPort {
    Order findOrderdById(String orderId);
    String findRestaurantIdByOrderId(String orderId);
}
