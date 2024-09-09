package com.froi.restaurant.order.application.makeorderusecase;

import com.froi.restaurant.common.UseCase;
import com.froi.restaurant.dish.domain.Dish;
import com.froi.restaurant.order.domain.Order;
import com.froi.restaurant.order.domain.exceptions.OrderException;
import com.froi.restaurant.order.infrastructure.inputports.restapi.MakeOrderInputPort;
import com.froi.restaurant.order.infrastructure.outputadapters.db.OrderDbOutputAdapter;
import com.froi.restaurant.restaurant.domain.Restaurant;
import com.froi.restaurant.restaurant.infrastructure.outputadapters.RestaurantDbOutputAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UseCase
public class MakeOrderUseCase implements MakeOrderInputPort {

    private OrderDbOutputAdapter orderDbOutputAdapter;
    private RestaurantDbOutputAdapter restaurantDbOutputAdapter;

    @Autowired
    public MakeOrderUseCase(OrderDbOutputAdapter orderDbOutputAdapter, RestaurantDbOutputAdapter restaurantDbOutputAdapter) {
        this.orderDbOutputAdapter = orderDbOutputAdapter;
        this.restaurantDbOutputAdapter = restaurantDbOutputAdapter;
    }

    @Override
    public String makeOrder(MakeOrderRequest makeOrderRequest) throws OrderException {
        Restaurant restaurant = restaurantDbOutputAdapter.findRestaurantById(makeOrderRequest.getRestaurantId());

        List<Dish> orderDishes = new ArrayList<>();
        for (OrderDish orderDish : makeOrderRequest.getOrderDishes()) {
            orderDishes.add(restaurantDbOutputAdapter.findRestaurantDish(restaurant.getId().toString(), orderDish));
        }

        Order order = makeOrderRequest.toDomain();
        order.setRestaurant(restaurant);
        order.setDate(LocalDateTime.now());
        order.setOrderDetail(orderDishes);
        // discounts are not implemented yet
        order.validate();
        order.calculateTotal();

        Order orderEntity = orderDbOutputAdapter.makeOrder(order);
        return orderEntity.getId().toString();
    }
}
