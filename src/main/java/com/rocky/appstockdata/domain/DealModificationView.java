package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
public class DealModificationView {
    private LocalDate modifyDate;
    private int sellPercent;
    private long sellPrice;
    private int buyPercent;
    private long buyPrice;

    @Builder
    public DealModificationView(String modifyDate, int sellPercent, long sellPrice, int buyPercent, long buyPrice) {
        this.modifyDate = LocalDate.parse(modifyDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.sellPercent = sellPercent;
        this.sellPrice = sellPrice;
        this.buyPercent = buyPercent;
        this.buyPrice = buyPrice;
    }
}
