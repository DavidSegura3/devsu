package com.devsu.demo.ms.account.ms_account.exceptions.business;

import java.io.Serial;

public class InvalidTransactionValueException extends RuntimeException{

    private static final String ERROR_MESSAGE = "The transaction value must be greater than zero.";
    public InvalidTransactionValueException() {
        super(ERROR_MESSAGE);
    }
    @Serial
    private static final long serialVersionUID = 5935641350829740504L;
}
