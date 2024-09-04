package com.froi.restaurant.restaurant.domain;


import com.froi.restaurant.common.DomainEntity;
import com.froi.restaurant.dish.domain.Dish;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@DomainEntity
public class RestaurantDish {
    private Restaurant restaurant;
    private Dish dish;
    private boolean isEnable;
}
