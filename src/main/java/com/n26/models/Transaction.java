package com.n26.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.n26.helper.deserializers.Amount;
import com.n26.helper.deserializers.TimeStamp;
import java.math.BigDecimal;

public class Transaction {

    @JsonProperty
    @JsonDeserialize(using = Amount.class)
    private BigDecimal amount;

    @JsonProperty
    @JsonDeserialize(using = TimeStamp.class)
    private Long timestamp;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
