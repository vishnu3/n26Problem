package com.n26.cache.impl;

import com.n26.cache.TransactionAnalyticsCache;
import com.n26.models.Statistics;
import com.n26.models.Transaction;
import com.n26.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component("transactionCache")
public class TransactionAnalyticsImpl implements TransactionAnalyticsCache {

    int oneMinToMilli = 60000;
    int OneSecToMilli = 1000;
    int mapSize;
    int decimalScale = 2;

    @Autowired
    private Validator validator;

    private ConcurrentHashMap<Integer, Statistics> concurrentHashMap;

    @PostConstruct
    private void init() {
        mapSize = oneMinToMilli/OneSecToMilli;
        concurrentHashMap = new ConcurrentHashMap(mapSize);
        for (int i = 0; i < mapSize; i++) {
            Statistics statistics = new Statistics();
            statistics.reset();
            concurrentHashMap.put(i, statistics);
        }
    }

    @Override
    public void add(Transaction transaction) {
        validator.validateTransaction(transaction);
        final int key = calculateKey(transaction);
        final Statistics statistics = concurrentHashMap.get(key);
        synchronized (statistics) {
            if (validator.isValidStatistics(statistics)) {
                updateStatistics(statistics, transaction);
            } else {
                statistics.reset();
                createStatistics(statistics, transaction);
            }
        }
    }

    @Override
    public void delete() {
        concurrentHashMap.clear();
        init();
    }

    @Override
    public Statistics getStatistics() {
        final Statistics aggregatedStatistics = new Statistics();
        aggregatedStatistics.reset();

        final List<Statistics> statisticsList = concurrentHashMap.values().stream().
                filter(statistics -> {
                            synchronized (statistics) {
                                return validator.isValidStatistics(statistics);
                            }
                        }
                ).collect(Collectors.toList());

        statisticsList.forEach(statistics -> {
                    synchronized (statistics) {
                        combineAllStatistics(aggregatedStatistics, statistics);
                    }
                }
        );
        System.out.println(aggregatedStatistics.toString());

        aggregatedStatistics.finalCheck();

        return aggregatedStatistics;
    }

    private int calculateKey(final Transaction transaction) {
        final int key = (int) ((System.currentTimeMillis() - transaction.getTimestamp())/ oneMinToMilli);
        return key % mapSize;
    }

    private void updateStatistics(final Statistics statistics, final Transaction transaction) {
        statistics.setSum(statistics.getSum().add(transaction.getAmount()));
        statistics.setCount(statistics.getCount() + 1L);
        statistics.setAvg(statistics.getSum()
                .divide(BigDecimal.valueOf(statistics.getCount()), decimalScale, RoundingMode.HALF_UP));
        statistics.setMin(transaction.getAmount().min(statistics.getMin()));
        statistics.setMax(transaction.getAmount().max(statistics.getMax()));
        System.out.println("update statistics "+statistics.toString());
    }

    private Statistics createStatistics(final Statistics statistics, final Transaction transaction) {
        statistics.setMin(transaction.getAmount());
        statistics.setMax(transaction.getAmount());
        statistics.setCount(1L);
        statistics.setAvg(transaction.getAmount());
        statistics.setSum(transaction.getAmount());
        statistics.setTimestamp(transaction.getTimestamp());
        System.out.println(statistics.toString());
        return statistics;
    }

    private Statistics combineAllStatistics(Statistics aggregatedStatistics, Statistics statistics) {
        System.out.println("aggred aggregatedStatistics "+aggregatedStatistics.toString());
        System.out.println("static statistics "+statistics.toString());

        aggregatedStatistics.setSum(aggregatedStatistics.getSum().add(statistics.getSum()));
        aggregatedStatistics.setCount(aggregatedStatistics.getCount() + statistics.getCount());
        aggregatedStatistics.setAvg(aggregatedStatistics.getSum().
                divide(BigDecimal.valueOf(aggregatedStatistics.getCount()), decimalScale, RoundingMode.HALF_UP));
        aggregatedStatistics.setMax(aggregatedStatistics.getMax().max(statistics.getMax()));
        aggregatedStatistics.setMin(aggregatedStatistics.getMin().min(statistics.getMin()));
        return aggregatedStatistics;
    }
}
