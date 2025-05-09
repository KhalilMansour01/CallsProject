package com.example.backend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MissingValueException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MissingValueException(String message) {
        super(message);
    }
}