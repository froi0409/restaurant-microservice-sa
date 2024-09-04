package com.froi.restaurant.order.infrastructure.inputports;

import com.froi.restaurant.common.exceptions.NetworkMicroserviceException;
import com.froi.restaurant.order.application.paidorderusecase.PayOrderRequest;
import com.froi.restaurant.order.domain.exceptions.OrderException;

public interface PayOrderInputPort {
    byte[] payOrder(PayOrderRequest payOrderRequest) throws NetworkMicroserviceException, OrderException;
}
