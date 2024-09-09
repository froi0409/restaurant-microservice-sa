package com.froi.restaurant.restaurant.infrastructure.outputports.restapi;

import com.froi.restaurant.common.exceptions.NetworkMicroserviceException;

public interface FindHotelOutputPort {
    boolean existsHotel(String hotelId) throws NetworkMicroserviceException;
}
