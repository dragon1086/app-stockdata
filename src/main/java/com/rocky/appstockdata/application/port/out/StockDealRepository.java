package com.rocky.appstockdata.application.port.out;

import com.rocky.appstockdata.domain.DailyDeal;
import com.rocky.appstockdata.domain.dto.DailyDealRequestDTO;
import com.rocky.appstockdata.domain.dto.DailyDealSmallDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockDealRepository {
    List<DailyDeal> getDailyDeal(DailyDealRequestDTO dailyDealRequestDTO);

    String getEarliestDate(String companyName);

    List<String> getCompanyNames(String keyword);

    List<DailyDealSmallDTO> from3MonthsAgoToTodayDeals();

    List<DailyDealSmallDTO> from6MonthsAgoTo3MonthsAgoDeals();

    List<DailyDealSmallDTO> from9MonthsAgoTo6MonthsAgoDeals();

    List<DailyDealSmallDTO> from12MonthsAgoTo9MonthsAgoDeals();
}
