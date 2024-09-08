package com.froi.restaurant.dish.infrastructure.inputadapters.restapi;

import com.froi.restaurant.common.WebAdapter;
import com.froi.restaurant.dish.infrastructure.inputports.restapi.ExistsDishInputPort;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants/v1/dishes")
@WebAdapter
@SecurityRequirement(name = "bearerAuth")
public class DishControllerAdapter {
    private ExistsDishInputPort existsDishInputPort;

    @Autowired
    public DishControllerAdapter(ExistsDishInputPort existsDishInputPort) {
        this.existsDishInputPort = existsDishInputPort;
    }

    @RequestMapping(method = RequestMethod.HEAD, path = "/exists/{dishId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> existsDish(@PathVariable String dishId) {
        existsDishInputPort.existsDish(dishId);
        return ResponseEntity.ok().build();
    }
}
