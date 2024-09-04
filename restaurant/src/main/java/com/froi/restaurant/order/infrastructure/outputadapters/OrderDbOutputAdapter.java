package com.froi.restaurant.order.infrastructure.outputadapters;

import com.froi.restaurant.common.PersistenceAdapter;
import com.froi.restaurant.dish.domain.Dish;
import com.froi.restaurant.dish.infrastructure.outputadapters.DishDbEntityRepository;
import com.froi.restaurant.order.domain.Order;
import com.froi.restaurant.order.infrastructure.outputports.MakeOrderOutputPort;
import com.froi.restaurant.restaurant.infrastructure.outputadapters.RestaurantDbEntityRepository;
import com.froi.restaurant.restaurant.infrastructure.outputadapters.RestaurantDishDbEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
@Transactional
public class OrderDbOutputAdapter implements MakeOrderOutputPort {

    private OrderDbEntityRepository orderDbRepository;
    private OrderDetailDbEntityRepository orderDetailDbRepository;

    @Autowired
    public OrderDbOutputAdapter(OrderDbEntityRepository orderDbRepository, OrderDetailDbEntityRepository orderDetailDbRepository) {
        this.orderDbRepository = orderDbRepository;
        this.orderDetailDbRepository = orderDetailDbRepository;
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
}
