package com.froi.restaurant.order.infrastructure.inputports.restapi;


import com.froi.restaurant.order.application.makeorderusecase.MakeOrderRequest;
import com.froi.restaurant.order.domain.exceptions.OrderException;

public interface MakeOrderInputPort {
    String makeOrder(MakeOrderRequest makeOrderRequest) throws OrderException;
}
