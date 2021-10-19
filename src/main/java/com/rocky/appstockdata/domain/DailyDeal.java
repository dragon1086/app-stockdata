package com.rocky.appstockdata.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DailyDeal {
    private String itemCode;
    private String itemName;
    private Long closingPrice;
    private Long startPrice;
    private Long highPrice;
    private Long lowPrice;
    private Long tradeVolume;
    private Long tradeMoney;
    private String dealDate;
    private Long totalAmount;
    private String kospiKosdaq;
}
