package com.n26.services;

import com.n26.models.Transaction;

public interface TransactionService {
    void add(Transaction transaction);
    void delete();

}
