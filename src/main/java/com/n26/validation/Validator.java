package com.n26.validation;

import com.n26.exceptions.ExpiredTransactionException;
import com.n26.exceptions.FutureTransactionException;
import com.n26.exceptions.InvalidTransactionException;
import com.n26.models.Statistics;
import com.n26.models.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Validator {

    private int oneMinInMilli=60000; //aggregatorWindowSize

    public void validateTransaction(final Transaction transaction){
        if (Objects.isNull(transaction.getAmount()) || Objects.isNull(transaction.getTimestamp())) {
            throw new InvalidTransactionException("Transaction is Invalid");
        }
        final long currentTime = System.currentTimeMillis();
        if (currentTime - transaction.getTimestamp() < 0) {
            throw new FutureTransactionException("Transaction happens in the future");
        } else if (currentTime - transaction.getTimestamp() > oneMinInMilli) {
            throw new ExpiredTransactionException("Transaction expired - Transaction created more than one minute ago");
        }
    }

    public boolean isValidStatistics(final Statistics statistics) {

        final long currentTime = System.currentTimeMillis();
        return (currentTime - statistics.getTimestamp() <= oneMinInMilli);
    }

    public boolean isStatisticsEmpty(final Statistics statistics){
        boolean isEmpty = false;
        if(statistics.getSum()==null){
            isEmpty = true;
        }
        if(statistics.getAvg()==null){
            isEmpty = true;
        }
        if(statistics.getMax()==null){
            isEmpty = true;
        }
        if(statistics.getMin()==null){
            isEmpty = true;
        }
        if(statistics.getCount()==null){
            isEmpty = true;
        }
        if(statistics.getTimestamp()==null){
            isEmpty = true;
        }
        return isEmpty;
    }

    public  boolean isMapEmpty(final ConcurrentHashMap<Integer, Statistics> concurrentHashMap){
         boolean isEmpty = false;

         for(Statistics statistics : concurrentHashMap.values()){
             if(isStatisticsEmpty(statistics)){
                 isEmpty = true;
             }else{
                 return false;
             }
         }

        return isEmpty;

    }

}
