package com.rocky.appstockdata.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocky.appstockdata.application.port.in.DealDataUseCase;
import com.rocky.appstockdata.application.port.out.StockDealRepository;
import com.rocky.appstockdata.domain.DailyDeal;
import com.rocky.appstockdata.domain.dto.DailyDealDTOv1;
import com.rocky.appstockdata.domain.dto.DailyDealDTOv2;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DealDataService implements DealDataUseCase {
    private final StockDealRepository stockDealRepository;

    public DealDataService(StockDealRepository stockDealRepository) {
        this.stockDealRepository = stockDealRepository;
    }

    @Override
    public String from3MonthsAgoToTodayDeals(String id) {
        try {
            return createJsonResponse(id, stockDealRepository.from3MonthsAgoToTodayDeals());
        } catch (JsonProcessingException e) {
            return "{\"errorMessage\" :  \"검색 결과 없음\"}";
        }
    }

    @Override
    public String from6MonthsAgoTo3MonthsAgoDeals(String id) {
        try {
            return createJsonResponse(id, stockDealRepository.from6MonthsAgoTo3MonthsAgoDeals());
        } catch (JsonProcessingException e) {
            return "{\"errorMessage\" :  \"검색 결과 없음\"}";
        }
    }

    @Override
    public String from9MonthsAgoTo6MonthsAgoDeals(String id) {
        try {
            return createJsonResponse(id, stockDealRepository.from9MonthsAgoTo6MonthsAgoDeals());
        } catch (JsonProcessingException e) {
            return "{\"errorMessage\" :  \"검색 결과 없음\"}";
        }
    }

    @Override
    public String from12MonthsAgoTo9MonthsAgoDeals(String id) {
        try {
            return createJsonResponse(id, stockDealRepository.from12MonthsAgoTo9MonthsAgoDeals());
        } catch (JsonProcessingException e) {
            return "{\"errorMessage\" :  \"검색 결과 없음\"}";
        }
    }

    @Override
    public String from1MonthsAgoToTodayDeals(String id) {
        try {
            return createJsonResponse(id, stockDealRepository.from1MonthsAgoToTodayDeals());
        } catch (JsonProcessingException e) {
            return "{\"errorMessage\" :  \"검색 결과 없음\"}";
        }
    }

    @Override
    public String from2MonthsAgoTo1MonthsAgoDeals(String id) {
        try {
            return createJsonResponse(id, stockDealRepository.from2MonthsAgoTo1MonthsAgoDeals());
        } catch (JsonProcessingException e) {
            return "{\"errorMessage\" :  \"검색 결과 없음\"}";
        }
    }


    private String createJsonResponse(String id, List<DailyDeal> results) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //간편한 결과를 원하시는 고객님 전용
        if("woo2015".equals(id)){
            List<DailyDealDTOv2> smallResult = results.stream().map(result -> result.toDailyDealDTOv2()).collect(Collectors.toList());
            return mapper.writeValueAsString(smallResult);
        } else {
            List<DailyDealDTOv1> fullResult = results.stream().map(result -> result.toDailyDealDTOv1()).collect(Collectors.toList());
            return mapper.writeValueAsString(fullResult);
        }
    }
}
