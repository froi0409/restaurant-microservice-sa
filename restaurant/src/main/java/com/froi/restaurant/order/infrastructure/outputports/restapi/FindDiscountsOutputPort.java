package com.froi.restaurant.order.infrastructure.outputports.restapi;

import com.froi.restaurant.order.application.paidorderusecase.BillDetail;
import com.froi.restaurant.order.application.paidorderusecase.BillDiscount;

import java.time.LocalDate;

public interface FindDiscountsOutputPort {
    BillDiscount findDishDiscount(String dishId, LocalDate date);
    BillDiscount findCustomerDiscount(String customerNit, LocalDate date);

}
