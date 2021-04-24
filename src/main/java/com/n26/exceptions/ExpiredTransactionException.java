package com.n26.exceptions;

public class ExpiredTransactionException extends RuntimeException {
    public ExpiredTransactionException(final String message) {
        super(message);
    }
}
