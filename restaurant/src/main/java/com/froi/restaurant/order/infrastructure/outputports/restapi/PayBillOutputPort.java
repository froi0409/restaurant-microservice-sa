package com.froi.restaurant.order.infrastructure.outputports.restapi;

import com.froi.restaurant.common.exceptions.NetworkMicroserviceException;
import com.froi.restaurant.order.application.paidorderusecase.MakeBillRequest;

public interface PayBillOutputPort {
    byte[] payBill(MakeBillRequest makeBillRequest) throws NetworkMicroserviceException;
}
