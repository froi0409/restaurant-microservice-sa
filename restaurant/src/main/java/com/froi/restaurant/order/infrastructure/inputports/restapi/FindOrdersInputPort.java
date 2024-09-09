package com.froi.restaurant.order.infrastructure.inputports.restapi;

import com.froi.restaurant.order.application.findorderusecase.BestRestaurantOrdersResponse;
import com.froi.restaurant.order.application.findorderusecase.OrderCostsInfo;
import com.froi.restaurant.order.domain.Order;

import java.util.List;

public interface FindOrdersInputPort {
    Order findOrderById(String orderId);

    List<OrderCostsInfo> findOrderCostsInfo();

    List<Order> findOrdersByRestaurantId(String restaurantId);

    BestRestaurantOrdersResponse findBestRestaurantOrders();

}
