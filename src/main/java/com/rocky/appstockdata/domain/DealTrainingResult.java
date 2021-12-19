package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class DealTrainingResult {
    private Double earningRate;
    private Long earningAmount;
    private Long totalAmount;
    private Long sumOfPurchaseAmount;
    private Long sumOfSellingAmount;
    private Long sumOfCommission;
    private Integer sumOfPurchaseQuantity;
    private Integer sumOfSellingQuantity;
    private String itemName;
    private List<DailyDealHistory> dailyDealHistories;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public DealTrainingResult(Double earningRate,
                              Long earningAmount,
                              Long totalAmount,
                              Long sumOfPurchaseAmount,
                              Long sumOfSellingAmount,
                              Long sumOfCommission,
                              Integer sumOfPurchaseQuantity,
                              Integer sumOfSellingQuantity,
                              String itemName,
                              List<DailyDealHistory> dailyDealHistories,
                              LocalDate startDate,
                              LocalDate endDate) {
        this.earningRate = earningRate;
        this.earningAmount = earningAmount;
        this.totalAmount = totalAmount;
        this.sumOfPurchaseAmount = sumOfPurchaseAmount;
        this.sumOfSellingAmount = sumOfSellingAmount;
        this.sumOfCommission = sumOfCommission;
        this.sumOfPurchaseQuantity = sumOfPurchaseQuantity;
        this.sumOfSellingQuantity = sumOfSellingQuantity;
        this.itemName = itemName;
        this.dailyDealHistories = dailyDealHistories;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
