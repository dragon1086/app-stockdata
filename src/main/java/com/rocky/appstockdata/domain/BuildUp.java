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

    @Builder
    public BuildUp(Double earningRate, Long earningAmount, Long totalAmount) {
        this.earningRate = earningRate;
        this.earningAmount = earningAmount;
        this.totalAmount = totalAmount;
    }
}
