package com.example.backend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MissingIdException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MissingIdException(String message) {
        super(message);
    }
}