package com.froi.restaurant.restaurant.application.createrestaurantusecase;

import com.froi.restaurant.common.UseCase;
import com.froi.restaurant.common.exceptions.NetworkMicroserviceException;
import com.froi.restaurant.restaurant.domain.Restaurant;
import com.froi.restaurant.restaurant.domain.exceptions.RestaurantException;
import com.froi.restaurant.restaurant.infrastructure.inputports.restapi.CreateRestaurantInputPort;
import com.froi.restaurant.restaurant.infrastructure.outputadapters.RestaurantDbOutputAdapter;
import com.froi.restaurant.restaurant.infrastructure.outputadapters.restapi.RestaurantRestOutputAdapter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
public class CreateRestaurantUseCase implements CreateRestaurantInputPort {

    private RestaurantDbOutputAdapter restaurantDbOutputAdapter;
    private RestaurantRestOutputAdapter restaurantRestOutputAdapter;

    @Autowired
    public CreateRestaurantUseCase(RestaurantDbOutputAdapter restaurantDbOutputAdapter, RestaurantRestOutputAdapter restaurantRestOutputAdapter) {
        this.restaurantDbOutputAdapter = restaurantDbOutputAdapter;
        this.restaurantRestOutputAdapter = restaurantRestOutputAdapter;
    }

    @Override
    public void createRestaurant(CreateRestaurantRequest createRestaurantRequest) throws RestaurantException, NetworkMicroserviceException {
        Restaurant restaurant = createRestaurantRequest.toDomain();
        restaurant.validate();
        validateHotel(createRestaurantRequest);
        restaurantDbOutputAdapter.createRestaurant(restaurant);
    }

    private void validateHotel(CreateRestaurantRequest request) throws NetworkMicroserviceException {
        if (request.getHotel() != null
        && !restaurantRestOutputAdapter.existsHotel(request.getHotel())) {
            throw new EntityNotFoundException(String.format("Hotel with id %s not found", request.getHotel()));
        }
    }
}
