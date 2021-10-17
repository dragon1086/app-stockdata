package com.rocky.appstockdata.application;

import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.BuildUpSourceDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildUpCalculateUseCaseTest {
    @Test
    void calculateBuildUp() {
        BuildUpSourceDTO buildUpSourceDTO = BuildUpSourceDTO.builder()
                .companyName("testCompany")
                .buildupAmount(1000000L)
                .startDate("2021-10-15")
                .endDate("2021-10-17")
                .build();

        BuildUpCalculateUseCase buildUpCalculateUseCase = new BuildUpCalculateUseCase();

        BuildUp expectedResult = BuildUp.builder()
                .earningRate(21.5f)
                .earningAmount(15000L)
                .totalAmount(45000L)
                .build();

        BuildUp buildUp = buildUpCalculateUseCase.calculateBuildUp(buildUpSourceDTO);

        Assertions.assertThat(buildUp.getEarningAmount()).isEqualTo(expectedResult.getEarningAmount());
        Assertions.assertThat(buildUp.getEarningRate()).isEqualTo(expectedResult.getEarningRate());
        Assertions.assertThat(buildUp.getTotalAmount()).isEqualTo(expectedResult.getTotalAmount());
    }
}