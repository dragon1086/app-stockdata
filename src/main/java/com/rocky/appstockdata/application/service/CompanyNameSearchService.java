package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.application.port.in.CompanyNameSearchUseCase;
import com.rocky.appstockdata.application.port.out.StockDealRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyNameSearchService implements CompanyNameSearchUseCase {
    final StockDealRepository stockDealRepository;

    public CompanyNameSearchService(StockDealRepository stockDealRepository) {
        this.stockDealRepository = stockDealRepository;
    }

    @Override
    public List<String> getCompanyNames(String keyword) {
        return stockDealRepository.getCompanyNames(keyword);
    }
}
