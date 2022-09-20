package com.example.rentaperson.exception;

public class ApiException extends RuntimeException{

    public ApiException(String message) {
        super(message);
    }
}