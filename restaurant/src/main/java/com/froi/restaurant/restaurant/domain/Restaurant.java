package com.froi.restaurant.restaurant.domain;

import com.froi.restaurant.common.DomainEntity;
import com.froi.restaurant.restaurant.domain.exceptions.RestaurantException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Getter
@Setter
@Builder
@DomainEntity
public class Restaurant {
    private UUID id;
    private String name;
    private String phone1;
    private String phone2;
    private Integer city;
    private String hotel;

    public void validate() throws RestaurantException {
        if (name == null || name.isEmpty()) {
            throw new RestaurantException("Name cannot be empty");
        }
        if (phone1 == null || phone1.isEmpty()) {
            throw new RestaurantException("Phone 1 cannot be empty");
        }
        if (city == null) {
            throw new RestaurantException("City cannot be empty");
        }
        if (hotel != null && hotel.isEmpty()) {
            throw new RestaurantException("Hotel cannot be empty");
        }
        if (StringUtils.isNumeric(phone1)) {
            throw new RestaurantException(String.format("Phone 1 must be a number, %s is not a number", phone1));
        }
        if (phone2 != null && StringUtils.isNumeric(phone2)) {
            throw new RestaurantException(String.format("Phone 2 must be a number, %s is not a number", phone2));
        }
    }
}
