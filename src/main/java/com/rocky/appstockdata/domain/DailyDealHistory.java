package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DailyDealHistory {
    private long closingPrice;
    private long myAverageUnitPrice;
    private String dealDate;
    private int purchaseQuantity;
    private long buildupAmount;

    @Builder
    public DailyDealHistory(long closingPrice,
                            long myAverageUnitPrice,
                            String dealDate,
                            int purchaseQuantity,
                            long buildupAmount) {
        this.closingPrice = closingPrice;
        this.myAverageUnitPrice = myAverageUnitPrice;
        this.dealDate = dealDate;
        this.purchaseQuantity = purchaseQuantity;
        this.buildupAmount = buildupAmount;
    }
}
