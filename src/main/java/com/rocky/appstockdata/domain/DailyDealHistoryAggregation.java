package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class DailyDealHistoryAggregation {
    long sumOfPurchaseAmount;
    int sumOfPurchaseQuantity;
    long sumOfSellingAmount;
    int sumOfSellingQuantity;
    long sumOfCommission;
    long sumOfRealizedEarningAmount;
    long remainingSlotAmount;
    double remainingPortion;
    long myAverageUnitPrice;
    long finalClosingPrice;
    int sumOfMyQuantity;
    String initialDealDate;
    List<DailyDealHistory> dailyDealHistories;

    @Builder
    public DailyDealHistoryAggregation(long sumOfPurchaseAmount,
                                       int sumOfPurchaseQuantity,
                                       long sumOfSellingAmount,
                                       int sumOfSellingQuantity,
                                       long sumOfCommission,
                                       long sumOfRealizedEarningAmount,
                                       long remainingSlotAmount,
                                       double remainingPortion,
                                       long myAverageUnitPrice,
                                       long finalClosingPrice,
                                       int sumOfMyQuantity,
                                       String initialDealDate,
                                       List<DailyDealHistory> dailyDealHistories) {
        this.sumOfPurchaseAmount = sumOfPurchaseAmount;
        this.sumOfPurchaseQuantity = sumOfPurchaseQuantity;
        this.sumOfSellingAmount = sumOfSellingAmount;
        this.sumOfSellingQuantity = sumOfSellingQuantity;
        this.sumOfCommission = sumOfCommission;
        this.sumOfRealizedEarningAmount = sumOfRealizedEarningAmount;
        this.remainingSlotAmount = remainingSlotAmount;
        this.remainingPortion = remainingPortion;
        this.myAverageUnitPrice = myAverageUnitPrice;
        this.finalClosingPrice = finalClosingPrice;
        this.sumOfMyQuantity = sumOfMyQuantity;
        this.initialDealDate = initialDealDate;
        this.dailyDealHistories = dailyDealHistories;
    }

    public void addDailyDealHistory(DailyDealHistory dailyDealHistoryToAdd){
        this.dailyDealHistories.add(dailyDealHistoryToAdd);
    }

    public void updateFinalClosingPrice(Long closingPrice) {
        this.finalClosingPrice = closingPrice;
    }
}
