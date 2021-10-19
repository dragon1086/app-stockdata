package com.rocky.appstockdata.port.out;

import com.rocky.appstockdata.domain.BuildUpSourceDTO;
import com.rocky.appstockdata.domain.DailyDeal;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockDealRepository {
    List<DailyDeal> getDailyDeal(BuildUpSourceDTO buildUpSourceDTO);
}
