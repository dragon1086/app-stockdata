package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

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
                            String buildupAmount,
                            String startDate,
                            String endDate,
                            String simulationMode) {
        this.companyName = StringUtils.isEmpty(companyName) ? null : companyName;
        this.buildupAmount = StringUtils.isEmpty(buildupAmount) ? null : Long.parseLong(buildupAmount);
        this.startDate = StringUtils.isEmpty(startDate) ? null : startDate;
        this.endDate = StringUtils.isEmpty(endDate) ? null : endDate;
        this.simulationMode = StringUtils.isEmpty(simulationMode) ? null : simulationMode;
    }
}
