package com.devsu.demo.ms.account.ms_account.exceptions;


import com.devsu.demo.ms.account.ms_account.exceptions.business.ClientNotFoundException;
import com.devsu.demo.ms.account.ms_account.exceptions.business.InsufficientBalanceException;
import com.devsu.demo.ms.account.ms_account.exceptions.business.InvalidMovementTypeException;
import com.devsu.demo.ms.account.ms_account.exceptions.business.ResourceNotFoundException;
import com.devsu.demo.ms.account.ms_account.exceptions.errors.ErrorMessage;
import jakarta.validation.UnexpectedTypeException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@Slf4j
@ControllerAdvice
@Data
public class AccountHandlerException {

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

    @ExceptionHandler(value = {InvalidMovementTypeException.class})
    public ResponseEntity<ErrorMessage> globalInvalidTypeException(InvalidMovementTypeException exception, WebRequest request) {

        ErrorMessage message = ErrorMessage
                .builder()
                .statusCode(BAD_REQUEST.value())
                .message(exception.getMessage())
                .route(request.getDescription(false))
                .build();
        log.error(message.toString());
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorMessage> globalMissingRequestParameterException(MissingServletRequestParameterException exception, WebRequest request) {

        ErrorMessage message = ErrorMessage
                .builder()
                .statusCode(BAD_REQUEST.value())
                .message(exception.getMessage())
                .route(request.getDescription(false))
                .build();
        log.error(message.toString());
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

    @ExceptionHandler(value = {UnexpectedTypeException.class})
    public ResponseEntity<ErrorMessage> globalUnexpectedTypeException(UnexpectedTypeException exception, WebRequest request) {

        ErrorMessage message = ErrorMessage
                .builder()
                .statusCode(BAD_REQUEST.value())
                .message(exception.getMessage())
                .route(request.getDescription(false))
                .build();
        log.error(message.toString());
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

    @ExceptionHandler(value = {ClientNotFoundException.class})
    public ResponseEntity<ErrorMessage> globalUnexpectedTypeException(ClientNotFoundException exception, WebRequest request) {

        ErrorMessage message = ErrorMessage
                .builder()
                .statusCode(NOT_FOUND.value())
                .message(exception.getMessage())
                .route(request.getDescription(false))
                .build();
        log.error(message.toString());
        return new ResponseEntity<>(message, NOT_FOUND);
    }

    @ExceptionHandler(value = {InsufficientBalanceException.class})
    public ResponseEntity<ErrorMessage> globalInsufficientBalanceException(InsufficientBalanceException exception, WebRequest request) {

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