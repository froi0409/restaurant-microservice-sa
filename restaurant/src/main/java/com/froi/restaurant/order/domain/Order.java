package com.froi.restaurant.order.domain;

import com.froi.restaurant.common.DomainEntity;
import com.froi.restaurant.dish.domain.Dish;
import com.froi.restaurant.order.domain.exceptions.OrderException;
import com.froi.restaurant.restaurant.domain.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@DomainEntity
public class Order {
    private UUID id;
    private Restaurant restaurant;
    private String waiter;
    String orderName;
    private LocalDateTime date;
    private LocalDateTime paidDate;
    private List<Dish> orderDetail;
    private List<OrderDiscount> discounts;
    private double total;
    private double subtotal;

    public void validate() throws OrderException {
        if (paidDate != null && paidDate.isBefore(date)) {
            throw new OrderException("Paid date cannot be before order date");
        }
        if (orderDetail == null || orderDetail.isEmpty()) {
            throw new OrderException("Order must have at least one dish");
        }
    }

    public void calculateTotal() {
        total = orderDetail.stream().mapToDouble(Dish::getCurrentPrice).sum();
        if (discounts != null) {
            total -= discounts.stream().mapToDouble(OrderDiscount::getDiscount).sum();
        }
    }

}
