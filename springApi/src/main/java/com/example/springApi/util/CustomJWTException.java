package com.example.springApi.util;

public class CustomJWTException extends RuntimeException{

    public CustomJWTException(String message) {
        super(message);
    }
}
