package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class DailyDealHistory {
    private long closingPrice;
    private long startPrice;
    private long highPrice;
    private long lowPrice;
    private long tradeVolume;
    private long tradeMoney;
    private long myAverageUnitPrice;
    private String dealDate;
    private long dealDateForTimestamp;
    private int purchaseQuantity;
    private long buildupAmount;

    @Builder
    public DailyDealHistory(long closingPrice,
                            long myAverageUnitPrice,
                            String dealDate,
                            long dealDateForTimestamp,
                            int purchaseQuantity,
                            long buildupAmount,
                            long startPrice,
                            long highPrice,
                            long lowPrice,
                            long tradeVolume,
                            long tradeMoney) {
        this.closingPrice = closingPrice;
        this.myAverageUnitPrice = myAverageUnitPrice;
        this.dealDate = dealDate;
        this.dealDateForTimestamp = dealDateForTimestamp;
        this.purchaseQuantity = purchaseQuantity;
        this.buildupAmount = buildupAmount;
        this.startPrice = startPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.tradeVolume = tradeVolume;
        this.tradeMoney = tradeMoney;
    }
}
