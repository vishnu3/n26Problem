package com.n26.exceptions.global;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.n26.exceptions.ExpiredTransactionException;
import com.n26.exceptions.FutureTransactionException;
import com.n26.exceptions.InvalidTransactionException;
import com.n26.models.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.Date;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = FutureTransactionException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public final ErrorMessage futureTxnException(FutureTransactionException exception, WebRequest request){
        ErrorMessage message = new ErrorMessage(new Date(), "Transaction Date Is In Future", exception.getLocalizedMessage());
        return message;
    }

    @ExceptionHandler(value = {InvalidTransactionException.class,InvalidFormatException.class,
            NumberFormatException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public final ErrorMessage txnDataException(Exception exception, WebRequest request){
        ErrorMessage message = new ErrorMessage(new Date(), "Incorrect data Format", exception.getLocalizedMessage());
        return message;
    }

    @ExceptionHandler(value = {IOException.class,JsonMappingException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public final ErrorMessage invalidRequestException(Exception exception, WebRequest request){
        ErrorMessage message = new ErrorMessage(new Date(), "Invalid JSON", exception.getLocalizedMessage());
        return message;
    }

    @ExceptionHandler(value = ExpiredTransactionException.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public final ErrorMessage expiredTxnException(ExpiredTransactionException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(new Date(), "Transaction Expired", exception.getLocalizedMessage());
        return message;
    }

}