package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DealTrainingSourceDTO {
    private String companyName;
    private Long slotAmount;
    private Integer portion;

    @Builder
    public DealTrainingSourceDTO(String companyName, Long slotAmount, Integer portion) {
        this.companyName = companyName;
        this.slotAmount = slotAmount;
        this.portion = portion;
    }
}
