package com.rocky.appstockdata.application.port.out;

import com.rocky.appstockdata.domain.DailyDeal;
import com.rocky.appstockdata.domain.DailyDealRequestDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockDealRepository {
    List<DailyDeal> getDailyDeal(DailyDealRequestDTO dailyDealRequestDTO);

    String getEarliestDate(String companyName);
}
