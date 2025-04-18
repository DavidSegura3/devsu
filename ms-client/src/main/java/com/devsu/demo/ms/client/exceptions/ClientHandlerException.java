package com.devsu.demo.ms.client.exceptions;

import com.devsu.demo.ms.client.exceptions.business.ResourceNotFoundException;
import com.devsu.demo.ms.client.exceptions.errors.ErrorMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@Slf4j
@ControllerAdvice
@Data
public class ClientHandlerException {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ErrorMessage> globalNotFoundException(ResourceNotFoundException exception, WebRequest request) {

        ErrorMessage message = ErrorMessage
                .builder()
                .statusCode(NOT_FOUND.value())
                .message(exception.getMessage())
                .route(request.getDescription(false))
                .build();
        log.error(message.toString());
        return new ResponseEntity<>(message, NOT_FOUND);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<ErrorMessage> globalDataIntegrityViolationException(DataIntegrityViolationException exception, WebRequest request) {

        ErrorMessage message = ErrorMessage
                .builder()
                .statusCode(BAD_REQUEST.value())
                .message(exception.getMessage())
                .route(request.getDescription(false))
                .build();
        log.error(message.toString());
        return new ResponseEntity<>(message, BAD_REQUEST);
    }
}