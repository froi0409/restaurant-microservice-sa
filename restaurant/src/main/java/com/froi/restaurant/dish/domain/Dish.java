package com.froi.restaurant.dish.domain;

import com.froi.restaurant.common.DomainEntity;
import com.froi.restaurant.dish.domain.exceptions.DishException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@DomainEntity
public class Dish {
    private UUID id;
    private String name;
    private double cost;
    private double currentPrice;
    private String note;

    public void validate() throws DishException {
        if (name == null || name.isBlank()) {
            throw new DishException("Name cannot be null or empty");
        }
        if (cost < 0) {
            throw new DishException("Cost cannot be negative");
        }
        if (currentPrice < 0) {
            throw new DishException("Current price cannot be negative");
        }
    }
}
