package com.froi.restaurant.order.infrastructure.inputadapters.restapi;

import com.froi.restaurant.order.domain.Order;
import lombok.Value;

@Value
public class OrdersReportResponse {
    String orderId;
    String restaurantName;
    String orderDate;
    String orderPaidDate;
    String orderName;

    public static OrdersReportResponse fromDomain(Order order) {
        return new OrdersReportResponse(
                order.getId().toString(),
                order.getRestaurant().getName(),
                order.getDate().toString(),
                order.getPaidDate().toString(),
                order.getOrderName()
        );
    }

}
