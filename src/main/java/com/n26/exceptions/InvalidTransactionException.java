package com.n26.exceptions;


public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(final String message) {
        super(message);
    }
}
