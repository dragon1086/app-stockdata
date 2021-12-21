package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.application.port.in.DealTrainingUseCase;
import com.rocky.appstockdata.application.port.out.StockDealRepository;
import com.rocky.appstockdata.domain.*;
import com.rocky.appstockdata.domain.utils.DealTrainingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.rocky.appstockdata.domain.utils.BuildUpUtil.transformDate;

@Service
@Slf4j
public class DealTrainingCalculateService implements DealTrainingUseCase {
    private final double MIN_MINUS_FIFTY_PERCENT = 0.67d;
    private final double MAX_PLUS_FIFTY_PERCENT = 2.0d;

    private StockDealRepository stockDealRepository;
    public DealTrainingCalculateService(StockDealRepository stockDealRepository) {
        this.stockDealRepository = stockDealRepository;
    }

    @Override
    public DealTrainingResult initializeDailyDeal(DealTrainingSourceDTO dealTrainingSourceDTO) {
        long sumOfPurchaseAmount = 0;
        long remainingSlotAmount = dealTrainingSourceDTO.getSlotAmount();
        int remainingPortion = 100;
        double currentValuationPercent = 0.0d;
        long myAverageUnitPrice = 0L;
        long finalClosingPrice = 0L;
        int sumOfMyQuantity = 0;
        String initialDealDate = "";

        List<DailyDealHistory> dailyDealHistories = new ArrayList<>();

        //랜덤일자 ~ 그로부터 3년 전
        LocalDate endDate = DealTrainingUtil.getRandomDate();
        LocalDate startDate = endDate.minusYears(3);

        String endDateString = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String startDateString = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<DailyDeal> dailyDealList = stockDealRepository.getDailyDeal(DailyDealRequestDTO.builder()
                                                                    .companyName(dealTrainingSourceDTO.getCompanyName())
                                                                    .startDate(startDateString)
                                                                    .endDate(endDateString)
                                                            .build());

        DailyDeal nextTryDay = dailyDealList.remove(dailyDealList.size() - 1);

        Iterator<DailyDeal> dailyDeals = dailyDealList.iterator();
        while(dailyDeals.hasNext()){
            DailyDeal dailyDeal = dailyDeals.next();

            dailyDealHistories.add(DailyDealHistory.builder()
                    .dealDate(dailyDeal.getDealDate())
                    .dealDateForTimestamp(transformDate(dailyDeal.getDealDate()))
                    .closingPrice(dailyDeal.getClosingPrice())
                    .startPrice(dailyDeal.getStartPrice())
                    .highPrice(dailyDeal.getHighPrice())
                    .lowPrice(dailyDeal.getLowPrice())
                    .tradeVolume(dailyDeal.getTradeVolume())
                    .build());

            if(!dailyDeals.hasNext()){
                initialDealDate = dailyDeal.getDealDate();
                Random random = new Random();
                double valuationPercent = MIN_MINUS_FIFTY_PERCENT + (MAX_PLUS_FIFTY_PERCENT - MIN_MINUS_FIFTY_PERCENT) * random.nextDouble();

                long initialAverageUnitPrice = Math.round(dailyDeal.getClosingPrice() * valuationPercent);

                long initialAmount = Math.round((double)dealTrainingSourceDTO.getSlotAmount() * dealTrainingSourceDTO.getPortion() / 100);
                int initialBuyingQuantity = (int) Math.floor(initialAmount / (double)initialAverageUnitPrice);
                long purchaseAmount = initialAverageUnitPrice * initialBuyingQuantity;

                remainingSlotAmount -= purchaseAmount;
                remainingPortion -= dealTrainingSourceDTO.getPortion();
                sumOfPurchaseAmount += purchaseAmount;
                myAverageUnitPrice = Math.round(((myAverageUnitPrice * sumOfMyQuantity) + (initialAverageUnitPrice * initialBuyingQuantity)) / (double)(sumOfMyQuantity + initialBuyingQuantity));

                finalClosingPrice = dailyDeal.getClosingPrice();

                dailyDealHistories.add(DailyDealHistory.builder()
                        .dealDate(dailyDeal.getDealDate())
                        .dealDateForTimestamp(transformDate(dailyDeal.getDealDate()))
                        .closingPrice(dailyDeal.getClosingPrice())
                        .startPrice(dailyDeal.getStartPrice())
                        .highPrice(dailyDeal.getHighPrice())
                        .lowPrice(dailyDeal.getLowPrice())
                        .tradeVolume(dailyDeal.getTradeVolume())
                        .myAverageUnitPrice(initialAverageUnitPrice)
                        .closingPurchaseQuantity(initialBuyingQuantity)
                        .additionalBuyingQuantity(initialBuyingQuantity)
                        .additionalBuyingAmount(purchaseAmount)
                        .buyPrice(initialAverageUnitPrice)
                        .buyPercent(dealTrainingSourceDTO.getPortion())
                        .remainingAmount(remainingSlotAmount)
                        .build());
            }
        }

        //차트에 마지막 일봉 추가 -> 이걸로 사용자는 살지 말지 판단할 수 있음
        dailyDealHistories.add(DailyDealHistory.builder()
                .dealDate(nextTryDay.getDealDate())
                .dealDateForTimestamp(transformDate(nextTryDay.getDealDate()))
                .closingPrice(nextTryDay.getClosingPrice())
                .startPrice(nextTryDay.getStartPrice())
                .highPrice(nextTryDay.getHighPrice())
                .lowPrice(nextTryDay.getLowPrice())
                .tradeVolume(nextTryDay.getTradeVolume())
                .myAverageUnitPrice(myAverageUnitPrice)
                .build());

        currentValuationPercent = (sumOfPurchaseAmount !=0) ? Math.round((finalClosingPrice - myAverageUnitPrice)/(double)myAverageUnitPrice*100*100)/100 : 0.0d;

        return DealTrainingResult.builder()
                .itemName(dealTrainingSourceDTO.getCompanyName())
                .startDate(startDate)
                .endDate(DealTrainingUtil.transformToLocalDate(nextTryDay.getDealDate()))
                .dailyDealHistories(dailyDealHistories)
                .remainingSlotAmount(remainingSlotAmount)
                .remainingPortion(remainingPortion)
                .totalAmount(sumOfPurchaseAmount)
                .valuationPercent(currentValuationPercent)
                .averageUnitPrice(myAverageUnitPrice)
                .currentClosingPrice(finalClosingPrice)
                .nextTryDate(DealTrainingUtil.transformToDateFormat(nextTryDay.getDealDate()))
                .dealModifications(Collections.singletonList(DealModification.builder()
                        .modifyDate(DealTrainingUtil.transformToDateFormat(initialDealDate))
                        .buyPercent(String.valueOf(dealTrainingSourceDTO.getPortion()))
                        .buyPrice(String.valueOf(myAverageUnitPrice))
                        .build()))
                .build();
    }
}
