package com.rocky.appstockdata.application.port.in;

import com.rocky.appstockdata.domain.dto.DailyDealSmallDTO;

import java.util.List;

public interface DealDataUseCase {
    List<DailyDealSmallDTO> from3MonthsAgoToTodayDeals();

    List<DailyDealSmallDTO> from6MonthsAgoTo3MonthsAgoDeals();

    List<DailyDealSmallDTO> from9MonthsAgoTo6MonthsAgoDeals();

    List<DailyDealSmallDTO> from12MonthsAgoTo9MonthsAgoDeals();
}
