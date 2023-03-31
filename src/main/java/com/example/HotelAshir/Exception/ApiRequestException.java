package com.example.HotelAshir.Exception;

public class ApiRequestException extends RuntimeException {

    public ApiRequestException(String message) {
        super(message);
    }
}