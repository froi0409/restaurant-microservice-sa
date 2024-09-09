package com.froi.restaurant.restaurant.application.createrestaurantusecase;

import com.froi.restaurant.restaurant.domain.Restaurant;
import lombok.Value;

@Value
public class CreateRestaurantRequest {
    String name;
    String phone1;
    String phone2;
    String city;
    String hotel;

    public Restaurant toDomain() {
        return Restaurant.builder()
                .name(name)
                .phone1(phone1)
                .phone2(phone2)
                .city(Integer.parseInt(city))
                .hotel(hotel)
                .build();
    }
}
