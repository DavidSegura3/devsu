package com.devsu.demo.ms.account.ms_account.exceptions.technical;

public class RestClientException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Technical error rest client in %s";

    public RestClientException(String message) {
        super(String.format(ERROR_MESSAGE, message));
    }
}
