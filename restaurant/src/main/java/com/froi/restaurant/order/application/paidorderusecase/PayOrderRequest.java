package com.froi.restaurant.order.application.paidorderusecase;

import lombok.Value;

@Value
public class PayOrderRequest {
    private String orderId;
    private String customerNit;
    private String optionalCustomerDpi;
    private String optionalCustomerFirstName;
    private String optionalCustomerLastName;
    private String optionalCustomerBirthDate;
    private boolean hasDiscount;
}
