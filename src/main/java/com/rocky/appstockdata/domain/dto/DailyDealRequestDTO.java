package com.rocky.appstockdata.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyDealRequestDTO {
    private String companyName;
    private String startDate;
    private String endDate;

    @Builder
    public DailyDealRequestDTO(String companyName, String startDate, String endDate) {
        this.companyName = companyName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
