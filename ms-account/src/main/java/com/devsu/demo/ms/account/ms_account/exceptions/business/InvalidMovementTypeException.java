package com.devsu.demo.ms.account.ms_account.exceptions.business;

import java.io.Serial;

public class InvalidMovementTypeException extends RuntimeException{

    private static final String ERROR_MESSAGE = "Invalid document type. Must be 'withdrawal' or 'deposit'.";
    public InvalidMovementTypeException() {
        super(ERROR_MESSAGE);
    }
    @Serial
    private static final long serialVersionUID = 1524988872276483829L;
}
