package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.application.port.out.StockDealRepository;
import com.rocky.appstockdata.domain.DealModification;
import com.rocky.appstockdata.domain.DealTrainingResult;
import com.rocky.appstockdata.domain.DealTrainingSourceDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Profile("local")
class DealTrainingCalculateServiceTest {

    @Autowired
    StockDealRepository stockDealRepository;

    DealTrainingCalculateService dealTrainingCalculateService;

    @BeforeAll
    public void setup(){
        dealTrainingCalculateService = new DealTrainingCalculateService(stockDealRepository);
    }

    @Test
    public void modifyCalculate_buying(){
        DealTrainingResult actualResult = dealTrainingCalculateService.modifyDailyDeal(Fixtures.FIRST_DEAL_WITH_BUYING);

        assertResults(Fixtures.FIRST_RESULT, actualResult);
    }

    @Test
    public void modifyCalculate_buying_And_Selling(){
        DealTrainingResult actualResult = dealTrainingCalculateService.modifyDailyDeal(Fixtures.SECOND_DEAL_WITH_SELL);

        assertResults(Fixtures.SECOND_RESULT, actualResult);
    }

    private void assertResults(DealTrainingResult expectedResult, DealTrainingResult actualResult) {
        Assertions.assertThat(actualResult.getEarningRate()).isEqualTo(expectedResult.getEarningRate());
        Assertions.assertThat(actualResult.getEarningAmount()).isEqualTo(expectedResult.getEarningAmount());
        Assertions.assertThat(actualResult.getTotalAmount()).isEqualTo(expectedResult.getTotalAmount());
        Assertions.assertThat(actualResult.getSumOfPurchaseAmount()).isEqualTo(expectedResult.getSumOfPurchaseAmount());
        Assertions.assertThat(actualResult.getSumOfPurchaseQuantity()).isEqualTo(expectedResult.getSumOfPurchaseQuantity());
        Assertions.assertThat(actualResult.getSumOfSellingAmount()).isEqualTo(expectedResult.getSumOfSellingAmount());
        Assertions.assertThat(actualResult.getSumOfSellingQuantity()).isEqualTo(expectedResult.getSumOfSellingQuantity());
        Assertions.assertThat(actualResult.getRemainingPortion()).isEqualTo(expectedResult.getRemainingPortion());
        Assertions.assertThat(actualResult.getRemainingSlotAmount()).isEqualTo(expectedResult.getRemainingSlotAmount());
        Assertions.assertThat(actualResult.getAverageUnitPrice()).isEqualTo(expectedResult.getAverageUnitPrice());
        Assertions.assertThat(actualResult.getCurrentClosingPrice()).isEqualTo(expectedResult.getCurrentClosingPrice());
        Assertions.assertThat(actualResult.getValuationPercent()).isEqualTo(expectedResult.getValuationPercent());
        Assertions.assertThat(actualResult.getNextTryDate()).isEqualTo(expectedResult.getNextTryDate());
    }

    private static class Fixtures{
        public static final DealTrainingSourceDTO FIRST_DEAL_WITH_BUYING = DealTrainingSourceDTO.builder()
                .companyName("삼성전자")
                .endDate("2005-09-01")
                .startDate("2002-09-01")
                .portion("10")
                .slotAmount("10000000")
                .dealModifications(Arrays.asList(DealModification.builder()
                                .modifyDate("2005-09-01")
                                .sellPrice("0")
                                .sellPercent("0")
                                .buyPercent("10")
                                .buyPrice("11000")
                                .build(),
                        DealModification.builder()
                                .modifyDate("2005-08-31")
                                .sellPrice("0")
                                .sellPercent("0")
                                .buyPercent("10")
                                .buyPrice("10771")
                                .build()))
                .build();

        public static final DealTrainingSourceDTO SECOND_DEAL_WITH_SELL = DealTrainingSourceDTO.builder()
                .companyName("삼성전자")
                .endDate("2005-09-02")
                .startDate("2002-09-01")
                .portion("10")
                .slotAmount("10000000")
                .dealModifications(Arrays.asList(DealModification.builder()
                                .modifyDate("2005-09-02")
                                .sellPrice("11140")
                                .sellPercent("100")
                                .buyPercent("0")
                                .buyPrice("0")
                                .build(),
                        DealModification.builder()
                                .modifyDate("2005-09-01")
                                .sellPrice("0")
                                .sellPercent("0")
                                .buyPercent("10")
                                .buyPrice("11000")
                                .build(),
                        DealModification.builder()
                                .modifyDate("2005-08-31")
                                .sellPrice("0")
                                .sellPercent("0")
                                .buyPercent("10")
                                .buyPrice("10771")
                                .build()))
                .build();

        public static final DealTrainingResult FIRST_RESULT = DealTrainingResult.builder()
                .earningRate(0.0d)
                .earningAmount(0L)
                .totalAmount(2016560L)
                .sumOfPurchaseAmount(1980932L)
                .sumOfSellingAmount(0L)
                .sumOfCommission(0L)
                .sumOfPurchaseQuantity(182)
                .sumOfSellingQuantity(0)
                .itemName("삼성전자")
                .remainingSlotAmount(8019068L)
                .remainingPortion(80.19d)
                .valuationPercent(1.8d)
                .averageUnitPrice(10884L)
                .currentClosingPrice(11080L)
                .nextTryDate("2005-09-02")
                .build();

        public static final DealTrainingResult SECOND_RESULT = DealTrainingResult.builder()
                .earningRate(0.41d)
                .earningAmount(40510L)
                .totalAmount(0L)
                .sumOfPurchaseAmount(1980932L)
                .sumOfSellingAmount(2027480L)
                .sumOfCommission(6082L)
                .sumOfPurchaseQuantity(182)
                .sumOfSellingQuantity(182)
                .itemName("삼성전자")
                .remainingSlotAmount(10040466L)
                .remainingPortion(100.4d)
                .valuationPercent(3.09d)
                .averageUnitPrice(10884L)
                .currentClosingPrice(11220L)
                .nextTryDate("2005-09-05")
                .build();
    }

}