package com.n26.services.impl;

import com.n26.cache.TransactionAnalyticsCache;
import com.n26.models.Statistics;
import com.n26.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    @Qualifier("transactionCache")
    private TransactionAnalyticsCache cache;

    @Override
    public Statistics get() {
        return cache.getStatistics();
    }
}
