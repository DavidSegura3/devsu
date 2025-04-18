package com.devsu.demo.ms.client.exceptions.business;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException{

    private static final String ERROR_MESSAGE = "Doesn't exist record in %s with id: %s";

    public ResourceNotFoundException(String message, Long id) {
        super(String.format(ERROR_MESSAGE, message, id));
    }

    public ResourceNotFoundException(String message, String value) {
        super(String.format(ERROR_MESSAGE, message, value));
    }

    @Serial
    private static final long serialVersionUID = 636674325335085608L;
}
