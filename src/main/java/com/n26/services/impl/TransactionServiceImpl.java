package com.n26.services.impl;

import com.n26.cache.TransactionAnalyticsCache;
import com.n26.models.Transaction;
import com.n26.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    @Qualifier("transactionCache")
    private TransactionAnalyticsCache cache;

    @Override
    public void add(Transaction transaction) {
        cache.add(transaction);
    }

    @Override
    public void delete() {
        cache.delete();
    }
}
