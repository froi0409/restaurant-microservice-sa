package com.froi.restaurant.restaurant.infrastructure.inputadapters;

import com.froi.restaurant.common.WebAdapter;
import com.froi.restaurant.common.exceptions.NetworkMicroserviceException;
import com.froi.restaurant.restaurant.application.createrestaurantusecase.CreateRestaurantRequest;
import com.froi.restaurant.restaurant.domain.exceptions.RestaurantException;
import com.froi.restaurant.restaurant.infrastructure.inputports.restapi.CreateRestaurantInputPort;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants/v1/restaurants")
@WebAdapter
@SecurityRequirement(name = "bearerAuth")
public class RestaurantControllerAdapter {

    private CreateRestaurantInputPort createRestaurantInputPort;

    @Autowired
    public RestaurantControllerAdapter(CreateRestaurantInputPort createRestaurantInputPort) {
        this.createRestaurantInputPort = createRestaurantInputPort;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createRestaurant(CreateRestaurantRequest createRestaurantRequest) throws RestaurantException, NetworkMicroserviceException {
        createRestaurantInputPort.createRestaurant(createRestaurantRequest);
        return ResponseEntity.ok().build();
    }

}
