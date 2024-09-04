package com.froi.restaurant.order.infrastructure.outputadapters.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.froi.restaurant.common.exceptions.NetworkMicroserviceException;
import com.froi.restaurant.order.application.paidorderusecase.MakeBillRequest;
import com.froi.restaurant.order.infrastructure.outputports.restapi.PayBillOutputPort;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BillRestApiOutputAdapter implements PayBillOutputPort {

    @Override
    public byte[] payBill(MakeBillRequest makeBillRequest) throws NetworkMicroserviceException {
        String url = "http://localhost:8084/payments/v1/bills/restaurant";

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonBody = objectMapper.writeValueAsString(makeBillRequest);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<byte[]> response =  restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    byte[].class
            );

            return response.getBody();
        } catch (Exception e) {
            throw new NetworkMicroserviceException(e.getMessage());
        }
    }
}
