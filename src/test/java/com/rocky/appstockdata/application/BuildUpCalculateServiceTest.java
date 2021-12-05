package com.rocky.appstockdata.application;

import com.rocky.appstockdata.application.port.out.StockDealRepository;
import com.rocky.appstockdata.application.service.BuildUpCalculateService;
import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.BuildUpSourceDTO;
import com.rocky.appstockdata.domain.DailyDealHistory;
import com.rocky.appstockdata.exceptions.NoResultDataException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Profile("local")
class BuildUpCalculateServiceTest {
    @Autowired
    StockDealRepository stockDealRepository;

    BuildUpCalculateService buildUpCalculateService;

    @BeforeAll
    public void setup(){
        buildUpCalculateService = new BuildUpCalculateService(stockDealRepository);
    }

    @Test
    void calculateBuildUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
                .sumOfPurchaseAmount(26480L)
                .dailyDealHistories(Arrays.asList(
                        DailyDealHistory.builder()
                                .closingPrice(3050)
                                .startPrice(2860)
                                .highPrice(3180)
                                .lowPrice(2840)
                                .tradeVolume(5828293)
                                .myAverageUnitPrice(3050)
                                .dealDate("20211013")
                                .dealDateForTimestamp(1634050800000L)
                                .purchaseQuantity(3)
                                .buildupAmount(850L)
                        .build(),
                        DailyDealHistory.builder()
                                .closingPrice(3965)
                                .startPrice(3060)
                                .highPrice(3965)
                                .lowPrice(3015)
                                .tradeVolume(17056503)
                                .myAverageUnitPrice(3416)
                                .dealDate("20211014")
                                .dealDateForTimestamp(1634137200000L)
                                .purchaseQuantity(2)
                                .buildupAmount(2920L)
                                .build(),
                        DailyDealHistory.builder()
                                .closingPrice(4700)
                                .startPrice(4245)
                                .highPrice(4945)
                                .lowPrice(4245)
                                .tradeVolume(50682633)
                                .myAverageUnitPrice(3783)
                                .dealDate("20211015")
                                .dealDateForTimestamp(1634223600000L)
                                .purchaseQuantity(2)
                                .buildupAmount(3520L)
                                .build()))
                .build();

        BuildUp buildUp = buildUpCalculateService.calculateBuildUp(buildUpSourceDTO);

        Assertions.assertThat(buildUp.getEarningAmount()).isEqualTo(expectedResult.getEarningAmount());
        Assertions.assertThat(buildUp.getEarningRate()).isEqualTo(expectedResult.getEarningRate());
        Assertions.assertThat(buildUp.getTotalAmount()).isEqualTo(expectedResult.getTotalAmount());
        Assertions.assertThat(buildUp.getSumOfPurchaseAmount()).isEqualTo(expectedResult.getSumOfPurchaseAmount());
        Assertions.assertThat(buildUp.getDailyDealHistories().toString()).isEqualTo(expectedResult.getDailyDealHistories().toString());
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
            BuildUp buildUp = buildUpCalculateService.calculateBuildUp(buildUpSourceDTO);
        }catch(NoResultDataException e){
            resultException = e;
        }

        Assertions.assertThat(resultException).isNotNull();
    }
}