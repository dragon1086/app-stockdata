package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DealTrainingSourceDTO {
    private String companyName;
    private Long slotAmount;
    private Double portion;
    private List<DealModification> dealModifications;
    private String startDate;
    private String endDate;

    @Builder
    public DealTrainingSourceDTO(String companyName,
                                 Long slotAmount,
                                 Double portion,
                                 List<DealModification> dealModifications,
                                 String startDate,
                                 String endDate) {
        this.companyName = companyName;
        this.slotAmount = slotAmount;
        this.portion = portion;
        this.dealModifications = dealModifications;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}