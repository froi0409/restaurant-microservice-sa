package com.froi.restaurant.order.application.findorderusecase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderCostsInfo {
    private String orderId;
    private String restaurantId;
    private String restaurantName;
    private String orderName;
    private String dishId;
    private String dishName;
    private Double dishCost;
    private LocalDateTime orderDate;
}
