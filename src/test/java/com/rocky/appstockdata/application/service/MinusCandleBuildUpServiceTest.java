package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.application.port.out.StockDealRepository;
import com.rocky.appstockdata.domain.*;
import com.rocky.appstockdata.domain.dto.BuildUpModificationSourceDTO;
import com.rocky.appstockdata.domain.dto.BuildUpSourceDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static com.rocky.appstockdata.domain.utils.BuildUpUtil.transformDate;
import static java.util.Arrays.asList;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Profile("local")
class MinusCandleBuildUpServiceTest {
    @Autowired
    StockDealRepository stockDealRepository;

    MinusCandleBuildUpService minusCandleBuildUpService;

    @BeforeAll
    public void setup(){
        minusCandleBuildUpService = new MinusCandleBuildUpService(stockDealRepository);
    }

    @Test
    void calculateBuildUp() {
        BuildUpSourceDTO buildUpSourceDTO = BuildUpSourceDTO.builder()
                .companyName("흥아해운")
                .buildupAmount("10000")
                .startDate("2021-11-01")
                .endDate("2021-11-03")
                .build();

        BuildUp expectedResult = BuildUp.builder()
                .earningRate(-2.06)
                .earningAmount(-412L)
                .totalAmount(19588L)
                .sumOfPurchaseAmount(17710L)
                .sumOfCommission(52L)
                .sumOfPurchaseQuantity(5)
                .sumOfSellingQuantity(5)
                .dailyDealHistories(asList(
                        DailyDealHistory.builder()
                                .closingPrice(3650L)
                                .startPrice(3730L)
                                .highPrice(3745L)
                                .lowPrice(3605L)
                                .tradeVolume(1437360L)
                                .myAverageUnitPrice(3650L)
                                .dealDate("20211101")
                                .dealDateForTimestamp(transformDate("20211101"))
                                .closingPurchaseQuantity(2)
                                .remainingAmount(2700L)
                        .build(),
                        DailyDealHistory.builder()
                                .closingPrice(3695L)
                                .startPrice(3655L)
                                .highPrice(3830L)
                                .lowPrice(3655L)
                                .tradeVolume(2038633L)
                                .myAverageUnitPrice(3650L)
                                .dealDate("20211102")
                                .dealDateForTimestamp(transformDate("20211102"))
                                .closingPurchaseQuantity(0)
                                .remainingAmount(2700L)
                                .build(),
                        DailyDealHistory.builder()
                                .closingPrice(3470L)
                                .startPrice(3680L)
                                .highPrice(3680L)
                                .lowPrice(3465L)
                                .tradeVolume(1866582L)
                                .myAverageUnitPrice(3542L)
                                .dealDate("20211103")
                                .dealDateForTimestamp(transformDate("20211103"))
                                .closingPurchaseQuantity(3)
                                .remainingAmount(2290L)
                                .build()))
                .build();

        BuildUp buildUp = minusCandleBuildUpService.calculateBuildUp(buildUpSourceDTO);

        Assertions.assertThat(buildUp.getEarningAmount()).isEqualTo(expectedResult.getEarningAmount());
        Assertions.assertThat(buildUp.getEarningRate()).isEqualTo(expectedResult.getEarningRate());
        Assertions.assertThat(buildUp.getTotalAmount()).isEqualTo(expectedResult.getTotalAmount());
        Assertions.assertThat(buildUp.getSumOfPurchaseAmount()).isEqualTo(expectedResult.getSumOfPurchaseAmount());
        Assertions.assertThat(buildUp.getDailyDealHistories().toString()).isEqualTo(expectedResult.getDailyDealHistories().toString());
    }

    @Test
    void buildUpModificationController_normal() {
        BuildUpModificationSourceDTO buildUpModificationSourceDTO = BuildUpModificationSourceDTO.builder()
                .companyName("흥아해운")
                .buildupAmount(10000L)
                .startDate("2021-11-01")
                .endDate("2021-11-03")
                .dealModifications(asList(DealModification.builder()
                                                .modifyDate("2021-11-02")
                                                .buyPercent("60")
                                                .buyPrice("3660")
                                                .build(),
                                        DealModification.builder()
                                                .modifyDate("2021-11-03")
                                                .buyPercent("40")
                                                .buyPrice("3500")
                                                .sellPercent("30")
                                                .sellPrice("3600")
                                                .build()))
                .build();

        BuildUp expectedResult = BuildUp.builder()
                .earningRate(-1.94)
                .earningAmount(-526L)
                .totalAmount(26634L)
                .sumOfPurchaseAmount(24870L)
                .sumOfSellingAmount(24420L)
                .sumOfCommission(73L)
                .sumOfPurchaseQuantity(7)
                .sumOfSellingQuantity(7)
                .dailyDealHistories(asList(
                        DailyDealHistory.builder()
                                .closingPrice(3650L)
                                .startPrice(3730L)
                                .highPrice(3745L)
                                .lowPrice(3605L)
                                .tradeVolume(1437360L)
                                .myAverageUnitPrice(3650L)
                                .dealDate("20211101")
                                .dealDateForTimestamp(transformDate("20211101"))
                                .closingPurchaseQuantity(2)
                                .remainingAmount(2700L)
                                .build(),
                        DailyDealHistory.builder()
                                .closingPrice(3695L)
                                .startPrice(3655L)
                                .highPrice(3830L)
                                .lowPrice(3655L)
                                .tradeVolume(2038633L)
                                .myAverageUnitPrice(3653L)
                                .dealDate("20211102")
                                .dealDateForTimestamp(transformDate("20211102"))
                                .closingPurchaseQuantity(0)
                                .remainingAmount(2700L)
                                .additionalBuyingQuantity(1)
                                .additionalBuyingAmount(3660L)
                                .build(),
                        DailyDealHistory.builder()
                                .closingPrice(3470L)
                                .startPrice(3680L)
                                .highPrice(3680L)
                                .lowPrice(3465L)
                                .tradeVolume(1866582L)
                                .myAverageUnitPrice(3543L)
                                .dealDate("20211103")
                                .dealDateForTimestamp(transformDate("20211103"))
                                .closingPurchaseQuantity(3)
                                .remainingAmount(2290L)
                                .additionalBuyingQuantity(1)
                                .additionalBuyingAmount(3500L)
                                .additionalSellingQuantity(1)
                                .additionalSellingAmount(3600L)
                                .commission(11L)
                                .realizedEarningAmount(-26L)
                                .build()))
                .build();

        BuildUp buildUp = minusCandleBuildUpService.calculateBuildUpModification(buildUpModificationSourceDTO);

        Assertions.assertThat(buildUp.getEarningAmount()).isEqualTo(expectedResult.getEarningAmount());
        Assertions.assertThat(buildUp.getEarningRate()).isEqualTo(expectedResult.getEarningRate());
        Assertions.assertThat(buildUp.getTotalAmount()).isEqualTo(expectedResult.getTotalAmount());
        Assertions.assertThat(buildUp.getSumOfPurchaseAmount()).isEqualTo(expectedResult.getSumOfPurchaseAmount());
        Assertions.assertThat(buildUp.getDailyDealHistories().toString()).isEqualTo(expectedResult.getDailyDealHistories().toString());
        Assertions.assertThat(buildUp.getSumOfCommission().toString()).isEqualTo(expectedResult.getSumOfCommission().toString());
        Assertions.assertThat(buildUp.getSumOfPurchaseQuantity().toString()).isEqualTo(expectedResult.getSumOfPurchaseQuantity().toString());
        Assertions.assertThat(buildUp.getSumOfSellingQuantity().toString()).isEqualTo(expectedResult.getSumOfSellingQuantity().toString());
        Assertions.assertThat(buildUp.getSumOfSellingAmount().toString()).isEqualTo(expectedResult.getSumOfSellingAmount().toString());
    }
}