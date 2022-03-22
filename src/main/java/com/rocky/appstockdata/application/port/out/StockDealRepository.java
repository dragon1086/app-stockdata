package com.rocky.appstockdata.application.port.out;

import com.rocky.appstockdata.domain.DailyDeal;
import com.rocky.appstockdata.domain.dto.DailyDealRequestDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockDealRepository {
    List<DailyDeal> getDailyDeal(DailyDealRequestDTO dailyDealRequestDTO);

    String getEarliestDate(String companyName);

    List<DailyDeal> from3MonthsAgoToTodayDeals();

    List<DailyDeal> from6MonthsAgoTo3MonthsAgoDeals();

    List<DailyDeal> from9MonthsAgoTo6MonthsAgoDeals();

    List<DailyDeal> from12MonthsAgoTo9MonthsAgoDeals();

    List<DailyDeal> from1MonthsAgoToTodayDeals();

    List<DailyDeal> from2MonthsAgoTo1MonthsAgoDeals();
}
