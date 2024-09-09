package com.froi.restaurant.order.infrastructure.outputports.db;

import com.froi.restaurant.order.domain.Order;

public interface MakeOrderOutputPort {
    Order makeOrder(Order order);
}
