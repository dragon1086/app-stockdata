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
    private String itemName;
    private List<DailyDealHistory> dailyDealHistories;

    @Builder
    public BuildUp(Double earningRate,
                   Long earningAmount,
                   Long totalAmount,
                   Long sumOfPurchaseAmount,
                   String itemName,
                   List<DailyDealHistory> dailyDealHistories) {
        this.earningRate = earningRate;
        this.earningAmount = earningAmount;
        this.totalAmount = totalAmount;
        this.sumOfPurchaseAmount = sumOfPurchaseAmount;
        this.itemName = itemName;
        this.dailyDealHistories = dailyDealHistories;
    }
}
