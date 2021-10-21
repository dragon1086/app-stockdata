package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildUp {
    private Double earningRate;
    private Long earningAmount;
    private Long totalAmount;
    private Long sumOfPurchaseAmount;

    @Builder
    public BuildUp(Double earningRate, Long earningAmount, Long totalAmount, Long sumOfPurchaseAmount) {
        this.earningRate = earningRate;
        this.earningAmount = earningAmount;
        this.totalAmount = totalAmount;
        this.sumOfPurchaseAmount = sumOfPurchaseAmount;
    }
}
