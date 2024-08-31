package com.rocky.appstockdata.domain.dto;

import com.rocky.appstockdata.domain.DealModification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Getter
@Setter
public class DealTrainingSourceDTO {
    private String companyName;
    private Long slotAmount;
    private Double portion;
    private Double initialPortion;
    private List<DealModification> dealModifications;
    private String startDate;
    private String endDate;
    private Double valuationPercent;
    private String level;
    private String jumpDate;

    @Builder
    public DealTrainingSourceDTO(String companyName,
                                 String slotAmount,
                                 String portion,
                                 String initialPortion,
                                 List<DealModification> dealModifications,
                                 String startDate,
                                 String endDate,
                                 String valuationPercent,
                                 String level,
                                 String jumpDate) {
        this.companyName = StringUtils.isEmpty(companyName) ? null : companyName;
        this.slotAmount = StringUtils.isEmpty(slotAmount) ? null : Long.parseLong(slotAmount);
        this.portion = StringUtils.isEmpty(portion) ? null : Double.parseDouble(portion);
        this.initialPortion = StringUtils.isEmpty(initialPortion) ? null : Double.parseDouble(initialPortion);
        this.dealModifications = dealModifications;
        this.startDate = StringUtils.isEmpty(startDate) ? null :startDate;
        this.endDate = StringUtils.isEmpty(endDate) ? null : endDate;
        this.valuationPercent = StringUtils.isEmpty(valuationPercent) ? null : Double.parseDouble(valuationPercent);
        this.level = level;
        this.jumpDate = StringUtils.isEmpty(jumpDate) ? null : jumpDate;
    }

    public DealTrainingSourceDTO createRandomCompanyName(String randomCompanyName) {
        this.companyName = randomCompanyName;

        return this;
    }
}
