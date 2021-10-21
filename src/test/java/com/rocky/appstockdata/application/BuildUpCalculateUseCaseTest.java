package com.rocky.appstockdata.application;

import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.BuildUpSourceDTO;
import com.rocky.appstockdata.exceptions.NoResultDataException;
import com.rocky.appstockdata.port.out.StockDealRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Profile("local")
class BuildUpCalculateUseCaseTest {
    @Autowired
    StockDealRepository stockDealRepository;

    BuildUpCalculateUseCase buildUpCalculateUseCase;

    @BeforeAll
    public void setup(){
        buildUpCalculateUseCase = new BuildUpCalculateUseCase(stockDealRepository);
    }

    @Test
    void calculateBuildUp() {
        BuildUpSourceDTO buildUpSourceDTO = BuildUpSourceDTO.builder()
                .companyName("흥아해운")
                .buildupAmount(10000L)
                .startDate("2021-10-13")
                .endDate("2021-10-15")
                .build();

        BuildUp expectedResult = BuildUp.builder()
                .earningRate(24.24)
                .earningAmount(6420L)
                .totalAmount(36420L)
                .build();

        BuildUp buildUp = buildUpCalculateUseCase.calculateBuildUp(buildUpSourceDTO);

        Assertions.assertThat(buildUp.getEarningAmount()).isEqualTo(expectedResult.getEarningAmount());
        Assertions.assertThat(buildUp.getEarningRate()).isEqualTo(expectedResult.getEarningRate());
        Assertions.assertThat(buildUp.getTotalAmount()).isEqualTo(expectedResult.getTotalAmount());
    }

    @Test
    void calculateBuildUp_with_no_data() {
        BuildUpSourceDTO buildUpSourceDTO = BuildUpSourceDTO.builder()
                .companyName("흥아해운")
                .buildupAmount(10000L)
                .startDate("1500-10-13")
                .endDate("1500-10-15")
                .build();

        Exception resultException = null;

        try{
            BuildUp buildUp = buildUpCalculateUseCase.calculateBuildUp(buildUpSourceDTO);
        }catch(NoResultDataException e){
            resultException = e;
        }

        Assertions.assertThat(resultException).isNotNull();
    }
}