package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@ToString
public class DealModification {
    private String modifyDate;
    private int sellPercent;
    private long sellPrice;
    private int buyPercent;
    private long buyPrice;

    @Builder
    public DealModification(String modifyDate, String sellPercent, String sellPrice, String buyPercent, String buyPrice) {
        if(StringUtils.isNotEmpty(modifyDate)){
            this.modifyDate = modifyDate;
        }
        if(StringUtils.isNotEmpty(sellPercent)){
            this.sellPercent = Integer.parseInt(sellPercent);
        }
        if(StringUtils.isNotEmpty(sellPrice)){
            this.sellPrice = Long.parseLong(sellPrice);
        }
        if(StringUtils.isNotEmpty(buyPercent)){
            this.buyPercent = Integer.parseInt(buyPercent);
        }
        if(StringUtils.isNotEmpty(buyPrice)){
            this.buyPrice = Long.parseLong(buyPrice);
        }
    }

    public DealModificationView transformForView(){
        return DealModificationView.builder()
                .modifyDate(this.modifyDate)
                .buyPercent(this.buyPercent)
                .buyPrice(this.buyPrice)
                .sellPercent(this.sellPercent)
                .sellPrice(this.sellPrice)
                .build();
    }
}
