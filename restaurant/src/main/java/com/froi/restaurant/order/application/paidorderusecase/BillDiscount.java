package com.froi.restaurant.order.application.paidorderusecase;

import lombok.Value;

@Value
public class BillDiscount {
    private String description;
    private double discounted;
}
