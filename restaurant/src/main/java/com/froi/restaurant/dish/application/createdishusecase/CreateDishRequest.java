package com.froi.restaurant.dish.application.createdishusecase;

import com.froi.restaurant.dish.domain.Dish;
import lombok.Value;

@Value
public class CreateDishRequest {
    String name;
    String cost;
    String currentPrice;

    public Dish toDomain() {
        return Dish.builder()
                .name(name)
                .cost(Double.parseDouble(cost))
                .currentPrice(Double.parseDouble(currentPrice))
                .build();
    }
}
