package com.froi.restaurant.restaurant.infrastructure.inputadapters;

import com.froi.restaurant.common.WebAdapter;
import com.froi.restaurant.common.exceptions.DuplicatedEntityException;
import com.froi.restaurant.common.exceptions.NetworkMicroserviceException;
import com.froi.restaurant.dish.domain.exceptions.DishException;
import com.froi.restaurant.restaurant.application.adddishtorestaurantusecase.AddDishToRestaurantRequest;
import com.froi.restaurant.restaurant.application.createrestaurantusecase.CreateRestaurantRequest;
import com.froi.restaurant.restaurant.domain.exceptions.RestaurantException;
import com.froi.restaurant.restaurant.infrastructure.inputports.restapi.AddDishToRestaurantInputPort;
import com.froi.restaurant.restaurant.infrastructure.inputports.restapi.CreateRestaurantInputPort;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants/v1/restaurants")
@WebAdapter
@SecurityRequirement(name = "bearerAuth")
public class RestaurantControllerAdapter {

    private CreateRestaurantInputPort createRestaurantInputPort;
    private AddDishToRestaurantInputPort addDishToRestaurantInputPort;

    @Autowired
    public RestaurantControllerAdapter(CreateRestaurantInputPort createRestaurantInputPort, AddDishToRestaurantInputPort addDishToRestaurantInputPort) {
        this.createRestaurantInputPort = createRestaurantInputPort;
        this.addDishToRestaurantInputPort = addDishToRestaurantInputPort;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createRestaurant(CreateRestaurantRequest createRestaurantRequest) throws RestaurantException, NetworkMicroserviceException {
        createRestaurantInputPort.createRestaurant(createRestaurantRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addDish")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addDishToRestaurant(@RequestBody AddDishToRestaurantRequest addDishToRestaurantRequest) throws DishException, DuplicatedEntityException {
        addDishToRestaurantInputPort.addDishToRestaurantInputPort(addDishToRestaurantRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

}
