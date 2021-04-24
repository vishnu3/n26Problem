package com.n26.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.n26.helper.serializer.BigDecimalAmount;
import java.math.BigDecimal;

public class Statistics {

    private static final BigDecimal MAX_VALUE = BigDecimal.valueOf(Long.MAX_VALUE);
    private static final BigDecimal MIN_VALUE = BigDecimal.valueOf(Long.MIN_VALUE);

    @JsonSerialize(using = BigDecimalAmount.class)
    private BigDecimal sum;

    @JsonSerialize(using = BigDecimalAmount.class)
    private BigDecimal avg;

    @JsonSerialize(using = BigDecimalAmount.class)
    private BigDecimal max;

    @JsonSerialize(using = BigDecimalAmount.class)
    private BigDecimal min;

    private Long count;

    @JsonIgnore
    private Long timestamp = 0L;

    @Override
    public String toString() {
        return "Statistics{" +
                "sum=" + sum +
                ", avg=" + avg +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                ", timestamp=" + timestamp +
                '}';
    }

    public static BigDecimal getMaxValue() {
        return MAX_VALUE;
    }

    public static BigDecimal getMinValue() {
        return MIN_VALUE;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void reset() {
        sum = BigDecimal.ZERO;
        avg = BigDecimal.ZERO;
        max = MIN_VALUE;
        min = MAX_VALUE;
        count = 0L;
        timestamp = 0L;
    }

    public void finalCheck(){
        if(min.equals(MIN_VALUE)){
            min = BigDecimal.ZERO;
        }
        if(max.equals(MIN_VALUE)){
            max = BigDecimal.ZERO;
        }
    }

    public void setDefaultValue(){
        sum = BigDecimal.ZERO;
        avg = BigDecimal.ZERO;
        max = BigDecimal.ZERO;
        min = BigDecimal.ZERO;
        count = 0L;
    }
}
