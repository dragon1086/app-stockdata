package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BuildUpHistoryAggregation {
    private long sumOfPurchaseAmount;
    private long sumOfSellingAmount;
    private long sumOfCommission;
    private long sumOfRealizedEarningAmount;
    private int sumOfPurchaseQuantity;
    private int sumOfSellingQuantity;
    private int sumOfMyQuantity;
    private double myAverageUnitPrice;
    private long finalRemainingAmount;
    private int sumOfAdditionalBuyingQuantityForToday;
    private int sumOfAdditionalSellingQuantityForToday;
    private long sumOfAdditionalBuyingAmountForToday;
    private long sumOfAdditionalSellingAmountForToday;
    private long sumOfCommissionForToday;
    private long sumOfRealizedEarningAmountForToday;
    private List<DailyDealHistory> dailyDealHistories;

    @Builder
    public BuildUpHistoryAggregation(long sumOfPurchaseAmount,
                                     long sumOfSellingAmount,
                                     long sumOfCommission,
                                     long sumOfRealizedEarningAmount,
                                     int sumOfPurchaseQuantity,
                                     int sumOfSellingQuantity,
                                     int sumOfMyQuantity,
                                     double myAverageUnitPrice,
                                     long finalRemainingAmount,
                                     int sumOfAdditionalBuyingQuantityForToday,
                                     int sumOfAdditionalSellingQuantityForToday,
                                     long sumOfAdditionalBuyingAmountForToday,
                                     long sumOfAdditionalSellingAmountForToday,
                                     long sumOfCommissionForToday,
                                     long sumOfRealizedEarningAmountForToday,
                                     List<DailyDealHistory> dailyDealHistories) {
        this.sumOfPurchaseAmount = sumOfPurchaseAmount;
        this.sumOfSellingAmount = sumOfSellingAmount;
        this.sumOfCommission = sumOfCommission;
        this.sumOfRealizedEarningAmount = sumOfRealizedEarningAmount;
        this.sumOfPurchaseQuantity = sumOfPurchaseQuantity;
        this.sumOfSellingQuantity = sumOfSellingQuantity;
        this.sumOfMyQuantity = sumOfMyQuantity;
        this.myAverageUnitPrice = myAverageUnitPrice;
        this.finalRemainingAmount = finalRemainingAmount;
        this.sumOfAdditionalBuyingQuantityForToday = sumOfAdditionalBuyingQuantityForToday;
        this.sumOfAdditionalSellingQuantityForToday = sumOfAdditionalSellingQuantityForToday;
        this.sumOfAdditionalBuyingAmountForToday = sumOfAdditionalBuyingAmountForToday;
        this.sumOfAdditionalSellingAmountForToday = sumOfAdditionalSellingAmountForToday;
        this.sumOfCommissionForToday = sumOfCommissionForToday;
        this.sumOfRealizedEarningAmountForToday = sumOfRealizedEarningAmountForToday;
        this.dailyDealHistories = dailyDealHistories;
    }

    public BuildUpHistoryAggregation updateSumForMinusCandleBuildUp(int sumOfPurchaseQuantity,
                                                                    int sumOfMyQuantity,
                                                                    long sumOfPurchaseAmount,
                                                                    double myAverageUnitPrice,
                                                                    long finalRemainingAmount,
                                                                    List<DailyDealHistory> dailyDealHistories){
        this.sumOfPurchaseQuantity = sumOfPurchaseQuantity;
        this.sumOfMyQuantity = sumOfMyQuantity;
        this.sumOfPurchaseAmount = sumOfPurchaseAmount;
        this.myAverageUnitPrice = myAverageUnitPrice;
        this.finalRemainingAmount = finalRemainingAmount;
        this.dailyDealHistories = dailyDealHistories;

        return this;
    }

    public BuildUpHistoryAggregation updateSellingSumForMinusCandleBuildUp(int sumOfSellingQuantity,
                                                                            long sumOfSellingAmount,
                                                                            long sumOfCommission,
                                                                            long sumOfRealizedEarningAmount){
        this.sumOfSellingQuantity = sumOfSellingQuantity;
        this.sumOfSellingAmount = sumOfSellingAmount;
        this.sumOfCommission = sumOfCommission;
        this.sumOfRealizedEarningAmount = sumOfRealizedEarningAmount;

        return this;
    }

    public BuildUpHistoryAggregation updateDailyHistories(List<DailyDealHistory> dailyDealHistories) {
        this.dailyDealHistories = dailyDealHistories;

        return this;
    }

    public void updateAggregations(BuildUpHistoryAggregation buildUpHistoryAggregation) {
        this.sumOfPurchaseAmount = buildUpHistoryAggregation.getSumOfPurchaseAmount();
        this.sumOfSellingAmount = buildUpHistoryAggregation.getSumOfSellingAmount();
        this.sumOfCommission = buildUpHistoryAggregation.getSumOfCommission();
        this.sumOfRealizedEarningAmount = buildUpHistoryAggregation.getSumOfRealizedEarningAmount();
        this.sumOfPurchaseQuantity = buildUpHistoryAggregation.getSumOfPurchaseQuantity();
        this.sumOfSellingQuantity = buildUpHistoryAggregation.getSumOfSellingQuantity();
        this.sumOfMyQuantity = buildUpHistoryAggregation.getSumOfMyQuantity();
        this.myAverageUnitPrice = buildUpHistoryAggregation.getMyAverageUnitPrice();
        this.finalRemainingAmount = buildUpHistoryAggregation.getFinalRemainingAmount();
        this.sumOfAdditionalBuyingQuantityForToday = buildUpHistoryAggregation.getSumOfAdditionalBuyingQuantityForToday();
        this.sumOfAdditionalSellingQuantityForToday = buildUpHistoryAggregation.getSumOfAdditionalSellingQuantityForToday();
        this.sumOfAdditionalBuyingAmountForToday = buildUpHistoryAggregation.getSumOfAdditionalBuyingAmountForToday();
        this.sumOfAdditionalSellingAmountForToday = buildUpHistoryAggregation.getSumOfAdditionalSellingAmountForToday();
        this.sumOfCommissionForToday = buildUpHistoryAggregation.getSumOfCommissionForToday();
        this.sumOfRealizedEarningAmountForToday = buildUpHistoryAggregation.getSumOfRealizedEarningAmountForToday();
        this.dailyDealHistories = buildUpHistoryAggregation.getDailyDealHistories();
    }
}
