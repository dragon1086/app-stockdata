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
    private List<DealModification> dealModifications;
    private String startDate;
    private String endDate;
    private Double valuationPercent;
    private String level;

    @Builder
    public DealTrainingSourceDTO(String companyName,
                                 String slotAmount,
                                 String portion,
                                 List<DealModification> dealModifications,
                                 String startDate,
                                 String endDate,
                                 String valuationPercent,
                                 String level) {
        this.companyName = StringUtils.isEmpty(companyName) ? null : companyName;
        this.slotAmount = StringUtils.isEmpty(slotAmount) ? null : Long.parseLong(slotAmount);
        this.portion = StringUtils.isEmpty(portion) ? null : Double.parseDouble(portion);
        this.dealModifications = dealModifications;
        this.startDate = StringUtils.isEmpty(startDate) ? null :startDate;
        this.endDate = StringUtils.isEmpty(endDate) ? null : endDate;
        this.valuationPercent = StringUtils.isEmpty(valuationPercent) ? null : Double.parseDouble(valuationPercent);
        this.level = level;
    }
}
