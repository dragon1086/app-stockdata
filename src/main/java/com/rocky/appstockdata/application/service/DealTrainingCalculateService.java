package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.application.port.in.DealTrainingUseCase;
import com.rocky.appstockdata.application.port.out.StockDealRepository;
import com.rocky.appstockdata.domain.*;
import com.rocky.appstockdata.domain.utils.DealTrainingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.rocky.appstockdata.domain.utils.BuildUpUtil.transformDate;

@Service
@Slf4j
public class DealTrainingCalculateService implements DealTrainingUseCase {
    StockDealRepository stockDealRepository;
    public DealTrainingCalculateService(StockDealRepository stockDealRepository) {
        this.stockDealRepository = stockDealRepository;
    }

    @Override
    public DealTrainingResult initializeDailyDeal(DealTrainingSourceDTO dealTrainingSourceDTO) {
        List<DailyDealHistory> dailyDealHistories = new ArrayList<>();

        LocalDate endDate = DealTrainingUtil.getRandomDate();
        LocalDate startDate = endDate.minusYears(3);

        String endDateString = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String startDateString = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<DailyDeal> dailyDealList = stockDealRepository.getDailyDeal(DailyDealRequestDTO.builder()
                                                                    .companyName(dealTrainingSourceDTO.getCompanyName())
                                                                    .startDate(startDateString)
                                                                    .endDate(endDateString)
                                                            .build());

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
        }

        return DealTrainingResult.builder()
                .itemName(dealTrainingSourceDTO.getCompanyName())
                .startDate(startDate)
                .endDate(endDate)
                .dailyDealHistories(dailyDealHistories)
                .build();
    }
}
