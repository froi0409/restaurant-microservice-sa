package com.froi.restaurant.dish.infrastructure.inputadapters.restapi;

import com.froi.restaurant.common.WebAdapter;
import com.froi.restaurant.dish.application.createdishusecase.CreateDishRequest;
import com.froi.restaurant.dish.domain.exceptions.DishException;
import com.froi.restaurant.dish.infrastructure.inputports.restapi.CreateDishInputPort;
import com.froi.restaurant.dish.infrastructure.inputports.restapi.ExistsDishInputPort;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants/v1/dishes")
@WebAdapter
@SecurityRequirement(name = "bearerAuth")
public class DishControllerAdapter {
    private ExistsDishInputPort existsDishInputPort;
    private CreateDishInputPort createDishInputPort;

    @Autowired
    public DishControllerAdapter(ExistsDishInputPort existsDishInputPort, CreateDishInputPort createDishInputPort) {
        this.existsDishInputPort = existsDishInputPort;
        this.createDishInputPort = createDishInputPort;
    }

    @RequestMapping(method = RequestMethod.HEAD, path = "/exists/{dishId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> existsDish(@PathVariable String dishId) {
        existsDishInputPort.existsDish(dishId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createDish(@RequestBody CreateDishRequest createDishRequest) throws DishException {
        createDishInputPort.createDish(createDishRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

}
