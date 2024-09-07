package com.froi.restaurant.order.infrastructure.outputadapters.db;

import com.froi.restaurant.common.PersistenceAdapter;
import com.froi.restaurant.dish.domain.Dish;
import com.froi.restaurant.order.domain.Order;
import com.froi.restaurant.order.infrastructure.outputports.db.FindOrderOutputPort;
import com.froi.restaurant.order.infrastructure.outputports.db.MakeOrderOutputPort;
import com.froi.restaurant.order.infrastructure.outputports.db.UpdateOrderOutputPort;
import com.froi.restaurant.restaurant.infrastructure.outputadapters.RestaurantDbOutputAdapter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@PersistenceAdapter
@Transactional
public class OrderDbOutputAdapter implements MakeOrderOutputPort, FindOrderOutputPort, UpdateOrderOutputPort {

    private OrderDbEntityRepository orderDbRepository;
    private OrderDetailDbEntityRepository orderDetailDbRepository;
    private RestaurantDbOutputAdapter restaurantDbOutputAdapter;
    @Autowired
    public OrderDbOutputAdapter(OrderDbEntityRepository orderDbRepository, OrderDetailDbEntityRepository orderDetailDbRepository, RestaurantDbOutputAdapter restaurantDbOutputAdapter) {
        this.orderDbRepository = orderDbRepository;
        this.orderDetailDbRepository = orderDetailDbRepository;
        this.restaurantDbOutputAdapter = restaurantDbOutputAdapter;
    }


    @Override
    public Order makeOrder(Order order) {
        OrderDbEntity orderDbEntity = OrderDbEntity.fromDomain(order);
        orderDbRepository.save(orderDbEntity);

        List<OrderDetailDbEntity> orderDetails = order.getOrderDetail().stream()
                .map((Dish dish) -> OrderDetailDbEntity.fromDomain(dish, orderDbEntity.getId()))
                .toList();

        orderDetailDbRepository.saveAll(orderDetails);

        return orderDbEntity.toDomain();
    }

    @Override
    public Order findOrderdById(String orderId) {
        Order order = orderDbRepository.findById(orderId).map(OrderDbEntity::toDomain)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id %s not found", orderId)));

        List<Dish> orderDetails = orderDetailDbRepository.findAllByOrder(orderId)
                .stream()
                .map(OrderDetailDbEntity::toDomainDishId)
                .toList();

        order.setOrderDetail(orderDetails);
        return order;
    }

    @Override
    public String findRestaurantIdByOrderId(String orderId) {
        return orderDbRepository.findRestaurantIdByOrderId(orderId);
    }


    @Override
    public void updateBill(String orderId) {
        OrderDbEntity orderDbEntity = orderDbRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id %s not found", orderId)));

        orderDbEntity.setPaidDate(LocalDateTime.now());
        orderDbRepository.save(orderDbEntity);
    }
}
