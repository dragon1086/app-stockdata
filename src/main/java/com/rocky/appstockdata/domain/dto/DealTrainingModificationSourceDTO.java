package com.rocky.appstockdata.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DealTrainingModificationSourceDTO {
    private String companyName;
    private String startDate;
    private String endDate;
    private String slotAmount;
    private String portion;
    private String initialPortion;
    private List<String> modifyDates;
    private List<String> sellPercents;
    private List<String> sellPrices;
    private List<String> buyPercents;
    private List<String> buyPrices;
    private String jumpDate;

    @Builder
    public DealTrainingModificationSourceDTO(String companyName,
                                             String startDate,
                                             String endDate,
                                             String slotAmount,
                                             String portion,
                                             List<String> modifyDates,
                                             List<String> sellPercents,
                                             List<String> sellPrices,
                                             List<String> buyPercents,
                                             List<String> buyPrices,
                                             String jumpDate) {
        this.companyName = companyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.slotAmount = slotAmount;
        this.portion = portion;
        this.modifyDates = modifyDates;
        this.sellPercents = sellPercents;
        this.sellPrices = sellPrices;
        this.buyPercents = buyPercents;
        this.buyPrices = buyPrices;
        this.jumpDate = jumpDate;
    }
}
