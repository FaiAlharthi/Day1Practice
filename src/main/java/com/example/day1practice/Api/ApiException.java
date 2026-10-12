package com.example.day1practice.Api;

public class ApiException extends RuntimeException{
    public ApiException (String message){
        super(message);
    }
}
