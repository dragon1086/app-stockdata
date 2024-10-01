package com.rocky.appstockdata.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rocky.appstockdata.domain.DealModification;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DealTrainingSourceDTO {
    @JsonProperty("company_name")
    private String companyName;
    @JsonProperty("slot_amount")
    private Long slotAmount;
    private Double portion;
    @JsonProperty("initial_portion")
    private Double initialPortion;
    @JsonProperty("deal_modifications")
    private List<DealModification> dealModifications;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("end_date")
    private String endDate;
    @JsonProperty("valuation_percent")
    private Double valuationPercent;
    private String level;
    @JsonProperty("jump_date")
    private String jumpDate;
    private Long id;
    @JsonProperty("user_id")
    private String userId;

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
                                 String jumpDate,
                                 Long id,
                                 String userId) {
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
        this.id = id;
        this.userId = userId;
    }

    public DealTrainingSourceDTO createRandomCompanyName(String randomCompanyName) {
        this.companyName = randomCompanyName;

        return this;
    }
}
