package com.froi.restaurant.order.infrastructure.outputports;

import com.froi.restaurant.order.domain.Order;

public interface MakeOrderOutputPort {
    Order makeOrder(Order order);
}
