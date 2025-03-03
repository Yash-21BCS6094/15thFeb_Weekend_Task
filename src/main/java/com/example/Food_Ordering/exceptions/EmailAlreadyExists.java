package com.example.Food_Ordering.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class EmailAlreadyExists extends IllegalArgumentException {
    public EmailAlreadyExists(String message) {
        super(message);
    }
}