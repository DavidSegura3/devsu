package com.devsu.demo.ms.account.ms_account.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public abstract class ValidationError {

    public static ResponseEntity<Map<String, Object>> createValidationErrorResponse(List<FieldError> fieldErrors) {
        final Map<String, Object> validations = new HashMap<>();
        final List<String> errorsList = fieldErrors.stream()
                .map(error -> String.format("Field: '%s' %s", error.getField(), error.getDefaultMessage()))
                .toList();
        validations.put("Errors List", errorsList);
        return new ResponseEntity<>(validations, HttpStatus.BAD_REQUEST);
    }
}
