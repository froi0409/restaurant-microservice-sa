package com.froi.restaurant.restaurant.infrastructure.outputadapters.restapi;

import com.froi.restaurant.common.exceptions.NetworkMicroserviceException;
import com.froi.restaurant.restaurant.infrastructure.outputports.restapi.FindHotelOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestaurantRestOutputAdapter implements FindHotelOutputPort {
    @Value("${hotels.url}")
    String hotelsUrl;

    private RestTemplate restTemplate;

    @Autowired
    public RestaurantRestOutputAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private boolean findEntity(String url) throws NetworkMicroserviceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<Void> response = restTemplate.exchange(
                    url,
                    HttpMethod.HEAD,
                    requestEntity,
                    Void.class
            );
            return response.getStatusCode() == HttpStatus.OK;
        } catch(HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;
            }
            throw new NetworkMicroserviceException(e.getMessage());
        } catch (Exception e) {
            throw new NetworkMicroserviceException(e.getMessage());
        }
    }

    @Override
    public boolean existsHotel(String hotelId) throws NetworkMicroserviceException {
        String url = hotelsUrl + "/v1/hotels/exists/" + hotelId;
        return findEntity(url);
    }
}
