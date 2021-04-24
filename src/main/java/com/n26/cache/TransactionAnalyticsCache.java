package com.n26.cache;

import com.n26.models.Statistics;
import com.n26.models.Transaction;

/*cache : to store and perform statistic on transaction data */

public interface TransactionAnalyticsCache {
    void add(Transaction transaction);

    void delete();

    Statistics getStatistics();
}
