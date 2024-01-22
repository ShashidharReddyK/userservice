package com.example.userservice.exceptions;

public class IncorrectUserCredentials extends Exception{
    public IncorrectUserCredentials(String message) {
        super(message);
    }
}
