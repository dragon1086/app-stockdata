package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BuildUpModificationSourceDTO {
    private String companyName;
    private Long buildupAmount;
    private String startDate;
    private String endDate;
    private List<DealModification> dealModifications;

    @Builder
    public BuildUpModificationSourceDTO(String companyName,
                                        Long buildupAmount,
                                        String startDate,
                                        String endDate,
                                        List<DealModification> dealModifications) {
        this.companyName = companyName;
        this.buildupAmount = buildupAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dealModifications = dealModifications;
    }
}
