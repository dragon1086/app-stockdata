package com.rocky.appstockdata.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DailyDealDTOv1 {
    private String itemCode;
    private String itemName;
    private String dealDate;
    private Long closingPrice;
    private Long startPrice;
    private Long highPrice;
    private Long lowPrice;
    private Long tradeVolume;
    private Long tradeMoney;
    private Long totalAmount;
    private String kospiKosdaq;

    @Builder
    public DailyDealDTOv1(String itemCode,
                          String itemName,
                          String dealDate,
                          Long closingPrice,
                          Long startPrice,
                          Long highPrice,
                          Long lowPrice,
                          Long tradeVolume,
                          Long tradeMoney,
                          Long totalAmount,
                          String kospiKosdaq) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.dealDate = dealDate;
        this.closingPrice = closingPrice;
        this.startPrice = startPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.tradeVolume = tradeVolume;
        this.tradeMoney = tradeMoney;
        this.totalAmount = totalAmount;
        this.kospiKosdaq = kospiKosdaq;
    }
}
