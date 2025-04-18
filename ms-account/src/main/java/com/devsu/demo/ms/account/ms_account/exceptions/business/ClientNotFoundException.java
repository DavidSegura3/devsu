package com.devsu.demo.ms.account.ms_account.exceptions.business;

import java.io.Serial;

public class ClientNotFoundException extends RuntimeException{

    private static final String ERROR_MESSAGE = "Doesn't exist client in '%s' with id: %s";

    public ClientNotFoundException(String message, Long id) {
        super(String.format(ERROR_MESSAGE, message, id));
    }

    @Serial
    private static final long serialVersionUID = 3897235359672691167L;
}
