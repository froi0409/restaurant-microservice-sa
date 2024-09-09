package com.froi.restaurant.order.infrastructure.outputports.db;

import com.froi.restaurant.order.application.findorderusecase.OrderCostsInfo;
import com.froi.restaurant.order.domain.Order;

import java.util.List;

public interface FindOrderOutputPort {
    Order findOrderdById(String orderId);
    String findRestaurantIdByOrderId(String orderId);
    List<OrderCostsInfo> findOrderCostsInfo();
}
