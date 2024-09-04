package com.froi.restaurant.order.application.paidorderusecase;

import lombok.Value;

@Value
public class BillDetail {
    private String description;
    private double amount;

}
