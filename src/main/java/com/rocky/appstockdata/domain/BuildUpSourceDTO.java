package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildUpSourceDTO {
    private String companyName;
    private Long buildupAmount;
    private String startDate;
    private String endDate;
    private String simulationMode;

    @Builder
    public BuildUpSourceDTO(String companyName,
                            Long buildupAmount,
                            String startDate,
                            String endDate,
                            String simulationMode) {
        this.companyName = companyName;
        this.buildupAmount = buildupAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.simulationMode = simulationMode;
    }
}
