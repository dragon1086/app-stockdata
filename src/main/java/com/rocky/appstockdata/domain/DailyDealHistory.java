package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private long remainingAmount;
    private int closingPurchaseQuantity;
    private int additionalBuyingQuantity;
    private long additionalBuyingAmount;
    private long realizedEarningAmount;
    private long commission;
    private int additionalSellingQuantity;
    private long additionalSellingAmount;

    @Builder
    public DailyDealHistory(long closingPrice,
                            long myAverageUnitPrice,
                            String dealDate,
                            long dealDateForTimestamp,
                            int closingPurchaseQuantity,
                            long remainingAmount,
                            long startPrice,
                            long highPrice,
                            long lowPrice,
                            long tradeVolume,
                            long tradeMoney,
                            int additionalBuyingQuantity,
                            long additionalBuyingAmount,
                            long realizedEarningAmount,
                            long commission,
                            int additionalSellingQuantity,
                            long additionalSellingAmount) {
        this.closingPrice = closingPrice;
        this.myAverageUnitPrice = myAverageUnitPrice;
        this.dealDate = dealDate;
        this.dealDateForTimestamp = dealDateForTimestamp;
        this.closingPurchaseQuantity = closingPurchaseQuantity;
        this.remainingAmount = remainingAmount;
        this.startPrice = startPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.tradeVolume = tradeVolume;
        this.tradeMoney = tradeMoney;
        this.additionalBuyingQuantity = additionalBuyingQuantity;
        this.additionalBuyingAmount = additionalBuyingAmount;
        this.realizedEarningAmount = realizedEarningAmount;
        this.commission = commission;
        this.additionalSellingQuantity = additionalSellingQuantity;
        this.additionalSellingAmount = additionalSellingAmount;
    }
}
