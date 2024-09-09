package com.froi.restaurant.common.exceptions;

public class NetworkMicroserviceException extends Exception{
    public NetworkMicroserviceException() {
    }

    public NetworkMicroserviceException(String message) {
        super(message);
    }
}
