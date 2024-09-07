package com.froi.restaurant.order.infrastructure.outputadapters.restapi;

import com.froi.restaurant.order.application.paidorderusecase.BillDiscount;
import com.froi.restaurant.order.infrastructure.outputports.restapi.FindDiscountsOutputPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Component
public class FindDiscountsOutputAdapter implements FindDiscountsOutputPort {

    @Value("${discounts.url}")
    private String discountsUrl;

    @Override
    public BillDiscount findDishDiscount(String dishId, LocalDate date) {
        String url = discountsUrl + "/v1/discounts/findDishDiscount/" + dishId + "/" + date;
        return getDiscount(url);
    }

    @Override
    public BillDiscount findCustomerDiscount(String customerNit, LocalDate date) {
        String url = discountsUrl + "/v1/discounts/findCustomerDiscount/" + customerNit + "/" + date;
        return getDiscount(url);
    }

    private BillDiscount getDiscount(String url) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<FindDiscountResponse> response = restTemplate.exchange(
                    url,
                    org.springframework.http.HttpMethod.GET,
                    entity,
                    FindDiscountResponse.class
            );
            System.out.println("Discount: " + response.getBody());
            return response.getBody().toBillDiscount();
        } catch(HttpClientErrorException e) {
            if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
                System.err.println("Find Discount Error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Find Discount Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
