package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildUp {
    private Float earningRate;
    private Long earningAmount;
    private Long totalAmount;

    @Builder
    public BuildUp(Float earningRate, Long earningAmount, Long totalAmount) {
        this.earningRate = earningRate;
        this.earningAmount = earningAmount;
        this.totalAmount = totalAmount;
    }
}
