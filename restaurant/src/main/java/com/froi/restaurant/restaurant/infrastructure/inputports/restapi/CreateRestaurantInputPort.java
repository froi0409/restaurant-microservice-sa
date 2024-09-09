package com.froi.restaurant.restaurant.infrastructure.inputports.restapi;

import com.froi.restaurant.common.exceptions.NetworkMicroserviceException;
import com.froi.restaurant.restaurant.application.createrestaurantusecase.CreateRestaurantRequest;
import com.froi.restaurant.restaurant.domain.exceptions.RestaurantException;

public interface CreateRestaurantInputPort {
    void createRestaurant(CreateRestaurantRequest createRestaurantRequest) throws RestaurantException, NetworkMicroserviceException;
}
