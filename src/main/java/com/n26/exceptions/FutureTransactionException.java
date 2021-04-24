package com.n26.exceptions;

public class FutureTransactionException extends RuntimeException {
    public FutureTransactionException(String message) {
        super(message);
    }
}
