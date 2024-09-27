package com.rocky.appstockdata.domain;

import com.rocky.appstockdata.domain.dto.DailyDealDTOv1;
import com.rocky.appstockdata.domain.dto.DailyDealDTOv2;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
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

    @Builder
    public DailyDeal(String itemCode,
                     String itemName,
                     Long closingPrice,
                     Long startPrice,
                     Long highPrice,
                     Long lowPrice,
                     Long tradeVolume,
                     Long tradeMoney,
                     String dealDate,
                     Long totalAmount,
                     String kospiKosdaq) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.closingPrice = closingPrice;
        this.startPrice = startPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.tradeVolume = tradeVolume;
        this.tradeMoney = tradeMoney;
        this.dealDate = dealDate;
        this.totalAmount = totalAmount;
        this.kospiKosdaq = kospiKosdaq;
    }

    public DailyDeal copy() {
        return DailyDeal.builder()
                .itemCode(this.itemCode)
                .itemName(this.itemName)
                .closingPrice(this.closingPrice)
                .startPrice(this.startPrice)
                .highPrice(this.highPrice)
                .lowPrice(this.lowPrice)
                .tradeVolume(this.tradeVolume)
                .tradeMoney(this.tradeMoney)
                .dealDate(this.dealDate)
                .totalAmount(this.totalAmount)
                .kospiKosdaq(this.kospiKosdaq)
                .build();
    }


    public DailyDealDTOv2 toDailyDealDTOv2() {
        return DailyDealDTOv2.builder()
                .dealDate(this.dealDate)
                .itemCode(this.itemCode)
                .itemName(this.itemName)
                .closingPrice(this.closingPrice)
                .startPrice(this.startPrice)
                .highPrice(this.highPrice)
                .lowPrice(this.lowPrice)
                .build();
    }

    public DailyDealDTOv1 toDailyDealDTOv1() {
        return DailyDealDTOv1.builder()
                .dealDate(this.dealDate)
                .itemCode(this.itemCode)
                .itemName(this.itemName)
                .closingPrice(this.closingPrice)
                .startPrice(this.startPrice)
                .highPrice(this.highPrice)
                .lowPrice(this.lowPrice)
                .tradeMoney(this.tradeMoney)
                .tradeVolume(this.tradeVolume)
                .totalAmount(this.totalAmount)
                .kospiKosdaq(this.kospiKosdaq)
                .build();
    }
}
