package com.devsu.demo.ms.account.ms_account.utils;


import com.devsu.demo.ms.account.ms_account.exceptions.business.InvalidMovementTypeException;

public abstract class ValidateMovementType {

    public static void validateMovementType(String movementType) {
        if (!"withdrawal".equalsIgnoreCase(movementType) && !"deposit".equalsIgnoreCase(movementType)) {
            throw new InvalidMovementTypeException();
        }
    }
}
