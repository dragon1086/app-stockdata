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

    public BuildUpHistoryAggregation updateSum(int sumOfPurchaseQuantity,
                                               int sumOfMyQuantity,
                                               long sumOfPurchaseAmount,
                                               double myAverageUnitPrice,
                                               long finalRemainingAmount,
                                               List<DailyDealHistory> dailyDealHistories){

        return BuildUpHistoryAggregation.builder()
                .sumOfPurchaseAmount(sumOfPurchaseAmount)
                .sumOfSellingAmount(this.sumOfSellingAmount)
                .sumOfCommission(this.sumOfCommission)
                .sumOfRealizedEarningAmount(this.sumOfRealizedEarningAmount)
                .sumOfPurchaseQuantity(sumOfPurchaseQuantity)
                .sumOfSellingQuantity(this.sumOfSellingQuantity)
                .sumOfMyQuantity(sumOfMyQuantity)
                .myAverageUnitPrice(myAverageUnitPrice)
                .finalRemainingAmount(finalRemainingAmount)
                .sumOfAdditionalBuyingQuantityForToday(this.sumOfAdditionalBuyingQuantityForToday)
                .sumOfAdditionalSellingQuantityForToday(this.sumOfAdditionalSellingQuantityForToday)
                .sumOfAdditionalBuyingAmountForToday(this.sumOfAdditionalBuyingAmountForToday)
                .sumOfAdditionalSellingAmountForToday(this.sumOfAdditionalSellingAmountForToday)
                .sumOfCommissionForToday(this.sumOfCommissionForToday)
                .sumOfRealizedEarningAmountForToday(this.sumOfRealizedEarningAmountForToday)
                .dailyDealHistories(dailyDealHistories)
                .build();
    }

    public BuildUpHistoryAggregation updateSumForAdditionalBuy(int sumOfPurchaseQuantity,
                                                               int sumOfMyQuantity,
                                                               long sumOfPurchaseAmount,
                                                               double myAverageUnitPrice,
                                                               int sumOfAdditionalBuyingQuantityForToday,
                                                               long sumOfAdditionalBuyingAmountForToday){

        return BuildUpHistoryAggregation.builder()
                .sumOfPurchaseAmount(sumOfPurchaseAmount)
                .sumOfSellingAmount(this.sumOfSellingAmount)
                .sumOfCommission(this.sumOfCommission)
                .sumOfRealizedEarningAmount(this.sumOfRealizedEarningAmount)
                .sumOfPurchaseQuantity(sumOfPurchaseQuantity)
                .sumOfSellingQuantity(this.sumOfSellingQuantity)
                .sumOfMyQuantity(sumOfMyQuantity)
                .myAverageUnitPrice(myAverageUnitPrice)
                .finalRemainingAmount(this.finalRemainingAmount)
                .sumOfAdditionalBuyingQuantityForToday(sumOfAdditionalBuyingQuantityForToday)
                .sumOfAdditionalSellingQuantityForToday(this.sumOfAdditionalSellingQuantityForToday)
                .sumOfAdditionalBuyingAmountForToday(sumOfAdditionalBuyingAmountForToday)
                .sumOfAdditionalSellingAmountForToday(this.sumOfAdditionalSellingAmountForToday)
                .sumOfCommissionForToday(this.sumOfCommissionForToday)
                .sumOfRealizedEarningAmountForToday(this.sumOfRealizedEarningAmountForToday)
                .dailyDealHistories(this.dailyDealHistories)
                .build();
    }

    public BuildUpHistoryAggregation updateSumForAdditionalSell(int sumOfSellingQuantity,
                                                                int sumOfMyQuantity,
                                                                long sumOfSellingAmount,
                                                                long sumOfCommission,
                                                                long sumOfRealizedEarningAmount,
                                                                int sumOfAdditionalSellingQuantityForToday,
                                                                long sumOfAdditionalSellingAmountForToday,
                                                                long sumOfCommissionForToday,
                                                                long sumOfRealizedEarningAmountForToday){

        return BuildUpHistoryAggregation.builder()
                .sumOfPurchaseAmount(this.sumOfPurchaseAmount)
                .sumOfSellingAmount(sumOfSellingAmount)
                .sumOfCommission(sumOfCommission)
                .sumOfRealizedEarningAmount(sumOfRealizedEarningAmount)
                .sumOfPurchaseQuantity(this.sumOfPurchaseQuantity)
                .sumOfSellingQuantity(sumOfSellingQuantity)
                .sumOfMyQuantity(sumOfMyQuantity)
                .myAverageUnitPrice(this.myAverageUnitPrice)
                .finalRemainingAmount(this.finalRemainingAmount)
                .sumOfAdditionalBuyingQuantityForToday(this.sumOfAdditionalBuyingQuantityForToday)
                .sumOfAdditionalSellingQuantityForToday(sumOfAdditionalSellingQuantityForToday)
                .sumOfAdditionalBuyingAmountForToday(this.sumOfAdditionalBuyingAmountForToday)
                .sumOfAdditionalSellingAmountForToday(sumOfAdditionalSellingAmountForToday)
                .sumOfCommissionForToday(sumOfCommissionForToday)
                .sumOfRealizedEarningAmountForToday(sumOfRealizedEarningAmountForToday)
                .dailyDealHistories(this.dailyDealHistories)
                .build();
    }

    public BuildUpHistoryAggregation updateSellingSum(int sumOfSellingQuantity,
                                                      long sumOfSellingAmount,
                                                      long sumOfCommission,
                                                      long sumOfRealizedEarningAmount){

        return BuildUpHistoryAggregation.builder()
                .sumOfPurchaseAmount(this.sumOfPurchaseAmount)
                .sumOfSellingAmount(sumOfSellingAmount)
                .sumOfCommission(sumOfCommission)
                .sumOfRealizedEarningAmount(sumOfRealizedEarningAmount)
                .sumOfPurchaseQuantity(this.sumOfPurchaseQuantity)
                .sumOfSellingQuantity(sumOfSellingQuantity)
                .sumOfMyQuantity(this.sumOfMyQuantity)
                .myAverageUnitPrice(this.myAverageUnitPrice)
                .finalRemainingAmount(this.finalRemainingAmount)
                .sumOfAdditionalBuyingQuantityForToday(this.sumOfAdditionalBuyingQuantityForToday)
                .sumOfAdditionalSellingQuantityForToday(this.sumOfAdditionalSellingQuantityForToday)
                .sumOfAdditionalBuyingAmountForToday(this.sumOfAdditionalBuyingAmountForToday)
                .sumOfAdditionalSellingAmountForToday(this.sumOfAdditionalSellingAmountForToday)
                .sumOfCommissionForToday(this.sumOfCommissionForToday)
                .sumOfRealizedEarningAmountForToday(this.sumOfRealizedEarningAmountForToday)
                .dailyDealHistories(this.dailyDealHistories)
                .build();
    }

    public BuildUpHistoryAggregation updateDailyHistories(List<DailyDealHistory> dailyDealHistories) {
        return BuildUpHistoryAggregation.builder()
                .sumOfPurchaseAmount(this.sumOfPurchaseAmount)
                .sumOfSellingAmount(this.sumOfSellingAmount)
                .sumOfCommission(this.sumOfCommission)
                .sumOfRealizedEarningAmount(this.sumOfRealizedEarningAmount)
                .sumOfPurchaseQuantity(this.sumOfPurchaseQuantity)
                .sumOfSellingQuantity(this.sumOfSellingQuantity)
                .sumOfMyQuantity(this.sumOfMyQuantity)
                .myAverageUnitPrice(this.myAverageUnitPrice)
                .finalRemainingAmount(this.finalRemainingAmount)
                .sumOfAdditionalBuyingQuantityForToday(this.sumOfAdditionalBuyingQuantityForToday)
                .sumOfAdditionalSellingQuantityForToday(this.sumOfAdditionalSellingQuantityForToday)
                .sumOfAdditionalBuyingAmountForToday(this.sumOfAdditionalBuyingAmountForToday)
                .sumOfAdditionalSellingAmountForToday(this.sumOfAdditionalSellingAmountForToday)
                .sumOfCommissionForToday(this.sumOfCommissionForToday)
                .sumOfRealizedEarningAmountForToday(this.sumOfRealizedEarningAmountForToday)
                .dailyDealHistories(dailyDealHistories)
                .build();
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

    public BuildUpHistoryAggregation initializeSumForToday() {
        return BuildUpHistoryAggregation.builder()
                .sumOfPurchaseAmount(this.sumOfPurchaseAmount)
                .sumOfSellingAmount(this.sumOfSellingAmount)
                .sumOfCommission(this.sumOfCommission)
                .sumOfRealizedEarningAmount(this.sumOfRealizedEarningAmount)
                .sumOfPurchaseQuantity(this.sumOfPurchaseQuantity)
                .sumOfSellingQuantity(this.sumOfSellingQuantity)
                .sumOfMyQuantity(this.sumOfMyQuantity)
                .myAverageUnitPrice(this.myAverageUnitPrice)
                .finalRemainingAmount(this.finalRemainingAmount)
                .sumOfAdditionalBuyingQuantityForToday(0)
                .sumOfAdditionalSellingQuantityForToday(0)
                .sumOfAdditionalBuyingAmountForToday(0L)
                .sumOfAdditionalSellingAmountForToday(0L)
                .sumOfCommissionForToday(0L)
                .sumOfRealizedEarningAmountForToday(0L)
                .dailyDealHistories(this.dailyDealHistories)
                .build();
    }

    public BuildUpHistoryAggregation copy() {
        return BuildUpHistoryAggregation.builder()
                .sumOfPurchaseAmount(this.sumOfPurchaseAmount)
                .sumOfSellingAmount(this.sumOfSellingAmount)
                .sumOfCommission(this.sumOfCommission)
                .sumOfRealizedEarningAmount(this.sumOfRealizedEarningAmount)
                .sumOfPurchaseQuantity(this.sumOfPurchaseQuantity)
                .sumOfSellingQuantity(this.sumOfSellingQuantity)
                .sumOfMyQuantity(this.sumOfMyQuantity)
                .myAverageUnitPrice(this.myAverageUnitPrice)
                .finalRemainingAmount(this.finalRemainingAmount)
                .sumOfAdditionalBuyingQuantityForToday(this.sumOfAdditionalBuyingQuantityForToday)
                .sumOfAdditionalSellingQuantityForToday(this.sumOfAdditionalSellingQuantityForToday)
                .sumOfAdditionalBuyingAmountForToday(this.sumOfAdditionalBuyingAmountForToday)
                .sumOfAdditionalSellingAmountForToday(this.sumOfAdditionalSellingAmountForToday)
                .sumOfCommissionForToday(this.sumOfCommissionForToday)
                .sumOfRealizedEarningAmountForToday(this.sumOfRealizedEarningAmountForToday)
                .dailyDealHistories(this.dailyDealHistories)
                .build();
    }
}
