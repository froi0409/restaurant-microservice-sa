package com.froi.restaurant.order.infrastructure.inputadapters.restapi;

import com.froi.restaurant.common.WebAdapter;
import com.froi.restaurant.common.exceptions.NetworkMicroserviceException;
import com.froi.restaurant.order.application.makeorderusecase.MakeOrderRequest;
import com.froi.restaurant.order.application.paidorderusecase.PayOrderRequest;
import com.froi.restaurant.order.domain.exceptions.OrderException;
import com.froi.restaurant.order.infrastructure.inputports.restapi.MakeOrderInputPort;
import com.froi.restaurant.order.infrastructure.inputports.restapi.PayOrderInputPort;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants/v1/order")
@WebAdapter
@SecurityRequirement(name = "bearerAuth")
public class OrderControllerAdapter {

    private MakeOrderInputPort makeOrderInputPort;
    private PayOrderInputPort payOrderInputPort;

    @Autowired
    public OrderControllerAdapter(MakeOrderInputPort makeOrderInputPort, PayOrderInputPort payOrderInputPort) {
        this.makeOrderInputPort = makeOrderInputPort;
        this.payOrderInputPort = payOrderInputPort;
    }

    @PostMapping("/make")
    @PreAuthorize("hasRole('RESTAURANT_EMPLOYEE')")
    public ResponseEntity<String> makeOrder(@RequestBody MakeOrderRequest makeOrderRequest) throws OrderException {
        String order = makeOrderInputPort.makeOrder(makeOrderRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(order);
    }

    @PostMapping("/pay")
    @PreAuthorize("hasRole('RESTAURANT_EMPLOYEE')")
    public ResponseEntity<byte[]> payOrder(@RequestBody PayOrderRequest payOrderRequest) throws OrderException, NetworkMicroserviceException, NetworkMicroserviceException {
        byte[] response = payOrderInputPort.payOrder(payOrderRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=factura_restaurante.pdf");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(response);
    }

}
