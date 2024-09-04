package com.froi.restaurant.order.infrastructure.inputadapters.restapi;

import com.froi.restaurant.common.WebAdapter;
import com.froi.restaurant.order.application.makeorderusecase.MakeOrderRequest;
import com.froi.restaurant.order.domain.exceptions.OrderException;
import com.froi.restaurant.order.infrastructure.inputports.MakeOrderInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants/v1/order")
@WebAdapter
public class OrderControllerAdapter {

    private MakeOrderInputPort makeOrderInputPort;

    @Autowired
    public OrderControllerAdapter(MakeOrderInputPort makeOrderInputPort) {
        this.makeOrderInputPort = makeOrderInputPort;
    }

    @PostMapping("/make")
    public ResponseEntity<Void> makeOrder(@RequestBody MakeOrderRequest makeOrderRequest) throws OrderException {
        makeOrderInputPort.makeOrder(makeOrderRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

}
