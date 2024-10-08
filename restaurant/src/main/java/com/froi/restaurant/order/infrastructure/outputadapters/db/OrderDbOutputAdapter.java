package com.froi.restaurant.order.infrastructure.outputadapters.db;

import com.froi.restaurant.common.PersistenceAdapter;
import com.froi.restaurant.dish.domain.Dish;
import com.froi.restaurant.order.application.findorderusecase.BestRestaurantOrdersResponse;
import com.froi.restaurant.order.application.findorderusecase.OrderCostsInfo;
import com.froi.restaurant.order.domain.Order;
import com.froi.restaurant.order.infrastructure.outputports.db.FindOrderOutputPort;
import com.froi.restaurant.order.infrastructure.outputports.db.MakeOrderOutputPort;
import com.froi.restaurant.order.infrastructure.outputports.db.UpdateOrderOutputPort;
import com.froi.restaurant.restaurant.domain.Restaurant;
import com.froi.restaurant.restaurant.infrastructure.outputadapters.RestaurantDbOutputAdapter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        OrderDbEntity orderDbEntity = orderDbRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id %s not found", orderId)));
        Order order = orderDbEntity.toDomain();

        List<Dish> orderDetails = orderDetailDbRepository.findAllByOrder(orderId)
                .stream()
                .map(OrderDetailDbEntity::toDomainDishId)
                .toList();

        Restaurant restaurant = restaurantDbOutputAdapter.findRestaurantById(orderDbEntity.getRestaurant());
        order.setRestaurant(restaurant);

        order.setOrderDetail(orderDetails);
        return order;
    }

    @Override
    public String findRestaurantIdByOrderId(String orderId) {
        return orderDbRepository.findRestaurantIdByOrderId(orderId);
    }

    @Override
    public List<OrderCostsInfo> findOrderCostsInfo() {
        return orderDbRepository.findOrderDetails();
    }

    @Override
    public List<Order> findOrdersByRestaurantId(String restaurantId) {
        List<OrderDbEntity> ordersDb = orderDbRepository.findAllByRestaurantAndPaidDateNotNull(restaurantId);
        if (ordersDb.isEmpty()) {
            throw new EntityNotFoundException(String.format("Orders with restaurant id %s not found", restaurantId));
        }
        List<Order> orders = new ArrayList<>();
        for (OrderDbEntity orderDbEntity : ordersDb) {
            Order order = orderDbEntity.toDomain();
            List<Dish> orderDetails = orderDetailDbRepository.findAllByOrder(orderDbEntity.getId())
                    .stream()
                    .map(OrderDetailDbEntity::toDomainDishId)
                    .toList();
            order.setOrderDetail(orderDetails);
            orders.add(order);
        }
        return orders;
    }

    @Override
    public BestRestaurantOrdersResponse findBestRestaurantOrders() {
        List<Object[]> bestRestaurant = orderDbRepository.findRestaurantTotalOrderSummaries();
        if (bestRestaurant.isEmpty()) {
            throw new EntityNotFoundException("Best restaurant not found");
        }
        Object[] bestRestaurantInfo = bestRestaurant.getFirst();
        String restaurantId = (String) bestRestaurantInfo[0];
        String restaurantName = (String) bestRestaurantInfo[1];
        BigDecimal totalOrders = (BigDecimal) bestRestaurantInfo[2];

        List<Order> orders = findOrdersByRestaurantId(restaurantId);

        return BestRestaurantOrdersResponse.builder()
                .restaurantId(restaurantId)
                .restaurantName(restaurantName)
                .totalOrders(totalOrders)
                .orders(orders)
                .build();
    }


    @Override
    public void updateBill(String orderId) {
        OrderDbEntity orderDbEntity = orderDbRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id %s not found", orderId)));

        orderDbEntity.setPaidDate(LocalDateTime.now());
        orderDbRepository.save(orderDbEntity);
    }
}
