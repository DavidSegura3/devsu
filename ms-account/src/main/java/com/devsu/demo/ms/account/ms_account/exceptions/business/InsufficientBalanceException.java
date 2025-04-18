package com.devsu.demo.ms.account.ms_account.exceptions.business;

import java.io.Serial;

public class InsufficientBalanceException extends RuntimeException{

    private static final String ERROR_MESSAGE = "Balance not available.";
    public InsufficientBalanceException() {
        super(ERROR_MESSAGE);
    }
    @Serial
    private static final long serialVersionUID = -4923590721676232060L;
}
