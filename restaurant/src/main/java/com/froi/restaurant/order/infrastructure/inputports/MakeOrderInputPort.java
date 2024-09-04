package com.froi.restaurant.order.infrastructure.inputports;


import com.froi.restaurant.order.application.makeorderusecase.MakeOrderRequest;
import com.froi.restaurant.order.domain.exceptions.OrderException;

public interface MakeOrderInputPort {
    void makeOrder(MakeOrderRequest makeOrderRequest) throws OrderException;
}
