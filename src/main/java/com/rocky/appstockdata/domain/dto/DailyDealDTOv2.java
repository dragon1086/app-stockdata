package com.rocky.appstockdata.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DailyDealDTOv2 {
    private String itemCode;
    private String itemName;
    private String dealDate;
    private Long closingPrice;
    private Long startPrice;
    private Long highPrice;
    private Long lowPrice;

    @Builder
    public DailyDealDTOv2(String itemCode,
                          String itemName,
                          String dealDate,
                          Long closingPrice,
                          Long startPrice,
                          Long highPrice,
                          Long lowPrice) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.dealDate = dealDate;
        this.closingPrice = closingPrice;
        this.startPrice = startPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
    }
}
