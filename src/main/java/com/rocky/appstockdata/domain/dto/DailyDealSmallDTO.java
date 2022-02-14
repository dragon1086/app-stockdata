package com.rocky.appstockdata.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DailyDealSmallDTO {
    private String itemCode;
    private String itemName;
    private String dealDate;
    private Long closingPrice;
    private Long startPrice;
    private Long highPrice;
    private Long lowPrice;
}
