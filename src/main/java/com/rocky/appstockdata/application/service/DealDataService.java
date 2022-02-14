package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.application.port.in.DealDataUseCase;
import com.rocky.appstockdata.application.port.out.StockDealRepository;
import com.rocky.appstockdata.domain.dto.DailyDealSmallDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealDataService implements DealDataUseCase {
    private final StockDealRepository stockDealRepository;

    public DealDataService(StockDealRepository stockDealRepository) {
        this.stockDealRepository = stockDealRepository;
    }

    @Override
    public List<DailyDealSmallDTO> from3MonthsAgoToTodayDeals() {
        return stockDealRepository.from3MonthsAgoToTodayDeals();
    }

    @Override
    public List<DailyDealSmallDTO> from6MonthsAgoTo3MonthsAgoDeals() {
        return stockDealRepository.from6MonthsAgoTo3MonthsAgoDeals();
    }

    @Override
    public List<DailyDealSmallDTO> from9MonthsAgoTo6MonthsAgoDeals() {
        return stockDealRepository.from9MonthsAgoTo6MonthsAgoDeals();
    }

    @Override
    public List<DailyDealSmallDTO> from12MonthsAgoTo9MonthsAgoDeals() {
        return stockDealRepository.from12MonthsAgoTo9MonthsAgoDeals();
    }
}
