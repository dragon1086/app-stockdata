package com.rocky.appstockdata.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DealTrainingHistoryDTO {
    private List<DealTrainingSourceDTO> dealTrainingSourceDTOs;
    private boolean isError;
    private String errorMessage;
    private String redirectUrl;

    @Builder
    public DealTrainingHistoryDTO(List<DealTrainingSourceDTO> dealTrainingSourceDTOs,
                                  boolean isError,
                                  String errorMessage,
                                  String redirectUrl) {
        this.dealTrainingSourceDTOs = dealTrainingSourceDTOs;
        this.isError = isError;
        this.errorMessage = errorMessage;
        this.redirectUrl = redirectUrl;
    }
}
