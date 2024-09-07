package com.froi.restaurant.order.infrastructure.outputadapters.restapi;

import com.froi.restaurant.order.application.paidorderusecase.BillDiscount;
import lombok.Value;

@Value
public class FindDiscountResponse {
    String discountDescription;
    int discountPercentage;

    public BillDiscount toBillDiscount() {
        return new BillDiscount(discountDescription, discountPercentage);
    }
}
