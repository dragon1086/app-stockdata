package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<DealModificationView> dealModificationsForView(){
        return dealModifications.stream()
                .map(DealModification::transformForView)
                .sorted((dealModificationView1, dealModificationView2) -> {
                    int result;
                    result = dealModificationView1.getModifyDate().getYear() - dealModificationView2.getModifyDate().getYear();

                    if(result == 0){
                        result = dealModificationView1.getModifyDate().getMonthValue() - dealModificationView2.getModifyDate().getMonthValue();
                    }

                    if(result == 0){
                        result = dealModificationView1.getModifyDate().getDayOfMonth() - dealModificationView2.getModifyDate().getDayOfMonth();
                    }

                    return result;
                })
                .collect(Collectors.toList());
    }
}