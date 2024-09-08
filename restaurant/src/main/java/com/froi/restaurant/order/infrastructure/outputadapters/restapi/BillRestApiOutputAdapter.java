package com.froi.restaurant.order.infrastructure.outputadapters.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.froi.restaurant.common.exceptions.NetworkMicroserviceException;
import com.froi.restaurant.order.application.paidorderusecase.MakeBillRequest;
import com.froi.restaurant.order.infrastructure.outputports.restapi.PayBillOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BillRestApiOutputAdapter implements PayBillOutputPort {

    @Value("${payments.url}")
    String paymentsUrl;

    private RestTemplate restTemplate;

    @Autowired
    public BillRestApiOutputAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public byte[] payBill(MakeBillRequest makeBillRequest) throws NetworkMicroserviceException {
        String url = paymentsUrl + "/v1/bills/restaurant";

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
