package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BuildUp {
    private Double earningRate;
    private Long earningAmount;
    private Long totalAmount;
    private Long sumOfPurchaseAmount;
    private Long sumOfSellingAmount;
    private Long sumOfCommission;
    private Integer sumOfPurchaseQuantity;
    private Integer sumOfSellingQuantity;
    private String itemName;
    private String simulationMode;
    private List<DailyDealHistory> dailyDealHistories;
    private List<DailyDealHistory> dailyDealHistoriesDesc;
    private int countOfDayOnDayClosingPriceIncrease;
    private int countOfDayOnDayClosingPriceDecrease;

    @Builder
    public BuildUp(Double earningRate,
                   Long earningAmount,
                   Long totalAmount,
                   Long sumOfPurchaseAmount,
                   Long sumOfSellingAmount,
                   Long sumOfCommission,
                   Integer sumOfPurchaseQuantity,
                   Integer sumOfSellingQuantity,
                   String itemName,
                   String simulationMode,
                   List<DailyDealHistory> dailyDealHistories,
                   List<DailyDealHistory> dailyDealHistoriesDesc,
                   int countOfDayOnDayClosingPriceIncrease,
                   int countOfDayOnDayClosingPriceDecrease) {
        this.earningRate = earningRate;
        this.earningAmount = earningAmount;
        this.totalAmount = totalAmount;
        this.sumOfPurchaseAmount = sumOfPurchaseAmount;
        this.sumOfSellingAmount = sumOfSellingAmount;
        this.sumOfCommission = sumOfCommission;
        this.sumOfPurchaseQuantity = sumOfPurchaseQuantity;
        this.sumOfSellingQuantity = sumOfSellingQuantity;
        this.itemName = itemName;
        this.simulationMode = simulationMode;
        this.dailyDealHistories = dailyDealHistories;
        this.dailyDealHistoriesDesc = dailyDealHistoriesDesc;
        this.countOfDayOnDayClosingPriceIncrease = countOfDayOnDayClosingPriceIncrease;
        this.countOfDayOnDayClosingPriceDecrease = countOfDayOnDayClosingPriceDecrease;
    }
}
