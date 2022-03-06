package com.rocky.appstockdata.domain;

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
    private MovingAverage movingAverage;

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
                     String kospiKosdaq,
                     MovingAverage movingAverage) {
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
        this.movingAverage = movingAverage;
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
                .movingAverage(this.movingAverage == null ? new MovingAverage(new HashMap<>()) : this.movingAverage)
                .build();
    }

    public void addMovingAverage(String window, long movingAverage) {
        this.movingAverage.addMovingAverage(window, movingAverage);
    }
}
