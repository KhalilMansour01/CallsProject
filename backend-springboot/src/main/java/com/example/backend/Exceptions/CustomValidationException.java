package com.example.backend.Exceptions;

import java.util.Map;

public class CustomValidationException extends RuntimeException {
    private final Map<String, String> fieldErrors;

    public CustomValidationException(Map<String, String> fieldErrors) {
        super("Validation failed");
        this.fieldErrors = fieldErrors;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}