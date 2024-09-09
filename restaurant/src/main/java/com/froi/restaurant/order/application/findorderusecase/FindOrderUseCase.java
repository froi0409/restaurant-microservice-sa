package com.froi.restaurant.order.application.findorderusecase;

import com.froi.restaurant.common.UseCase;
import com.froi.restaurant.order.domain.Order;
import com.froi.restaurant.order.infrastructure.inputports.restapi.FindOrdersInputPort;
import com.froi.restaurant.order.infrastructure.outputadapters.db.OrderDbOutputAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UseCase
public class FindOrderUseCase implements FindOrdersInputPort {

    private OrderDbOutputAdapter orderDbOutputAdapter;

    @Autowired
    public FindOrderUseCase(OrderDbOutputAdapter orderDbOutputAdapter) {
        this.orderDbOutputAdapter = orderDbOutputAdapter;
    }

    @Override
    public Order findOrderById(String orderId) {
        return orderDbOutputAdapter.findOrderdById(orderId);
    }

    @Override
    public List<OrderCostsInfo> findOrderCostsInfo() {
        return orderDbOutputAdapter.findOrderCostsInfo();
    }

    @Override
    public List<Order> findOrdersByRestaurantId(String restaurantId) {
        return orderDbOutputAdapter.findOrdersByRestaurantId(restaurantId);
    }

    @Override
    public BestRestaurantOrdersResponse findBestRestaurantOrders() {
        return orderDbOutputAdapter.findBestRestaurantOrders();
    }
}
