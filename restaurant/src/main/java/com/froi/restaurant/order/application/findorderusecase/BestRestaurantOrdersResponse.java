package com.froi.restaurant.order.application.findorderusecase;

import com.froi.restaurant.order.domain.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
public class BestRestaurantOrdersResponse {
    private String restaurantId;
    private String restaurantName;
    private BigDecimal totalOrders;
    private List<Order> orders;
}
