package com.froi.restaurant.order.domain;

import com.froi.restaurant.common.DomainEntity;
import com.froi.restaurant.order.domain.exceptions.OrderException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@DomainEntity
public class OrderDiscount {
    private UUID id;
    private String description;
    private double discount;

    public void validate() throws OrderException {
        if (description == null || description.isBlank()) {
            throw new OrderException("Description cannot be null or empty");
        }
        if (discount < 0) {
            throw new OrderException("Discount cannot be negative");
        }
    }
}
