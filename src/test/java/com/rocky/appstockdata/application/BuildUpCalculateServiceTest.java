package com.rocky.appstockdata.application;

import com.rocky.appstockdata.application.port.out.StockDealRepository;
import com.rocky.appstockdata.application.service.BuildUpCalculateService;
import com.rocky.appstockdata.domain.*;
import com.rocky.appstockdata.exceptions.NoResultDataException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static java.util.Arrays.asList;

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
        BuildUpSourceDTO buildUpSourceDTO = BuildUpSourceDTO.builder()
                .companyName("흥아해운")
                .buildupAmount(10000L)
                .startDate("2021-10-13")
                .endDate("2021-10-15")
                .build();

        BuildUp expectedResult = BuildUp.builder()
                .earningRate(23.87)
                .earningAmount(6321L)
                .totalAmount(36321L)
                .sumOfPurchaseAmount(26480L)
                .sumOfCommission(99L)
                .sumOfPurchaseQuantity(7)
                .sumOfSellingQuantity(7)
                .dailyDealHistories(asList(
                        DailyDealHistory.builder()
                                .closingPrice(3050L)
                                .startPrice(2860L)
                                .highPrice(3180L)
                                .lowPrice(2840L)
                                .tradeVolume(5828293L)
                                .myAverageUnitPrice(3050L)
                                .dealDate("20211013")
                                .dealDateForTimestamp(1634050800000L)
                                .closingPurchaseQuantity(3)
                                .remainingAmount(850L)
                        .build(),
                        DailyDealHistory.builder()
                                .closingPrice(3965L)
                                .startPrice(3060L)
                                .highPrice(3965L)
                                .lowPrice(3015L)
                                .tradeVolume(17056503L)
                                .myAverageUnitPrice(3416L)
                                .dealDate("20211014")
                                .dealDateForTimestamp(1634137200000L)
                                .closingPurchaseQuantity(2)
                                .remainingAmount(2920L)
                                .build(),
                        DailyDealHistory.builder()
                                .closingPrice(4700L)
                                .startPrice(4245L)
                                .highPrice(4945L)
                                .lowPrice(4245L)
                                .tradeVolume(50682633L)
                                .myAverageUnitPrice(3783L)
                                .dealDate("20211015")
                                .dealDateForTimestamp(1634223600000L)
                                .closingPurchaseQuantity(2)
                                .remainingAmount(3520L)
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

    @Test
    void buildUpModificationController_normal() {
        BuildUpModificationSourceDTO buildUpModificationSourceDTO = BuildUpModificationSourceDTO.builder()
                .companyName("흥아해운")
                .buildupAmount(10000L)
                .startDate("2021-10-13")
                .endDate("2021-10-15")
                .dealModifications(asList(DealModification.builder()
                                                .modifyDate("2021-10-14")
                                                .buyPercent("40")
                                                .buyPrice("3015")
                                                .build(),
                                        DealModification.builder()
                                                .modifyDate("2021-10-15")
                                                .buyPercent("40")
                                                .buyPrice("4245")
                                                .sellPercent("30")
                                                .sellPrice("4945")
                                                .build()))
                .build();

        BuildUp expectedResult = BuildUp.builder()
                .earningRate(25.72)
                .earningAmount(8948L)
                .totalAmount(46208L)
                .sumOfPurchaseAmount(33740L)
                .sumOfSellingAmount(42545L)
                .sumOfCommission(128L)
                .sumOfPurchaseQuantity(9)
                .sumOfSellingQuantity(9)
                .dailyDealHistories(asList(
                        DailyDealHistory.builder()
                                .closingPrice(3050L)
                                .startPrice(2860L)
                                .highPrice(3180L)
                                .lowPrice(2840L)
                                .tradeVolume(5828293L)
                                .myAverageUnitPrice(3050L)
                                .dealDate("20211013")
                                .dealDateForTimestamp(1634050800000L)
                                .closingPurchaseQuantity(3)
                                .remainingAmount(850L)
                                .build(),
                        DailyDealHistory.builder()
                                .closingPrice(3965L)
                                .startPrice(3060L)
                                .highPrice(3965L)
                                .lowPrice(3015L)
                                .tradeVolume(17056503L)
                                .myAverageUnitPrice(3349L)
                                .dealDate("20211014")
                                .dealDateForTimestamp(1634137200000L)
                                .closingPurchaseQuantity(2)
                                .remainingAmount(2920L)
                                .additionalBuyingQuantity(1)
                                .additionalBuyingAmount(3015L)
                                .build(),
                        DailyDealHistory.builder()
                                .closingPrice(4700)
                                .startPrice(4245)
                                .highPrice(4945)
                                .lowPrice(4245)
                                .tradeVolume(50682633)
                                .myAverageUnitPrice(3749)
                                .dealDate("20211015")
                                .dealDateForTimestamp(1634223600000L)
                                .closingPurchaseQuantity(2)
                                .remainingAmount(3520L)
                                .additionalBuyingQuantity(1)
                                .additionalBuyingAmount(4245L)
                                .additionalSellingQuantity(1)
                                .additionalSellingAmount(4945L)
                                .commission(15L)
                                .realizedEarningAmount(1453L)
                                .build()))
                .build();

        BuildUp buildUp = buildUpCalculateService.calculateBuildUpModification(buildUpModificationSourceDTO);

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