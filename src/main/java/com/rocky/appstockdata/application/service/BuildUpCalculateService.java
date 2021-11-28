package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.BuildUpSourceDTO;
import com.rocky.appstockdata.domain.DailyDeal;
import com.rocky.appstockdata.domain.DailyDealHistory;
import com.rocky.appstockdata.exceptions.NoResultDataException;
import com.rocky.appstockdata.application.port.in.BuildUpCalculateUseCase;
import com.rocky.appstockdata.application.port.out.StockDealRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildUpCalculateService implements BuildUpCalculateUseCase {
    StockDealRepository stockDealRepository;
    public BuildUpCalculateService(StockDealRepository stockDealRepository) {
        this.stockDealRepository = stockDealRepository;
    }


    @Override
    public BuildUp calculateBuildUp(BuildUpSourceDTO buildUpSourceDTO) {
        long sumOfPurchaseAmount = 0L;
        int sumOfPurchaseQuantity = 0;
        double myAverageUnitPrice;
        long finalClosingPrice = 0L;
        long finalRemainingAmount = 0L;
        double myEarningRate;
        long myEarningAmount;
        List<DailyDealHistory> dailyDealHistories = new ArrayList<>();

        List<DailyDeal> dailyDealList = stockDealRepository.getDailyDeal(buildUpSourceDTO);
        if(dailyDealList.isEmpty()){
            throw new NoResultDataException("조회하신 검색 결과가 없습니다. 종목명 또는 조회기간을 확인 부탁드립니다.\r\n"
                + "종목명은 영문표기를 한글로 변형 또는 반대로 해서 다시 입력해보시기 바랍니다.");
        }

        for(DailyDeal dailyDeal : dailyDealList){
            Long closingPrice = dailyDeal.getClosingPrice();

            long buildupAmount = buildUpSourceDTO.getBuildupAmount() + finalRemainingAmount;
            int purchaseQuantity = (int) Math.floor(buildupAmount / (double) closingPrice);
            long purchaseAmount = closingPrice * purchaseQuantity;

            sumOfPurchaseQuantity += purchaseQuantity;
            sumOfPurchaseAmount += purchaseAmount;

            if(sumOfPurchaseQuantity != 0L){
                dailyDealHistories.add(DailyDealHistory.builder()
                        .dealDate(dailyDeal.getDealDate())
                        .closingPrice(closingPrice)
                        .myAverageUnitPrice(Math.round(sumOfPurchaseAmount / (double)sumOfPurchaseQuantity))
                        .purchaseQuantity(purchaseQuantity)
                        .buildupAmount(buildupAmount - purchaseAmount)
                        .build());
            }

            finalClosingPrice = closingPrice;
            finalRemainingAmount = buildupAmount - purchaseAmount;
        }

        myAverageUnitPrice = sumOfPurchaseAmount / (double)sumOfPurchaseQuantity;
        myEarningRate = (finalClosingPrice - myAverageUnitPrice) / myAverageUnitPrice;
        myEarningAmount = Math.round(myEarningRate * sumOfPurchaseAmount);

        return BuildUp.builder()
                .earningRate(Double.parseDouble(String.format("%.2f",myEarningRate * 100)))
                .earningAmount(myEarningAmount)
                .totalAmount(sumOfPurchaseAmount + myEarningAmount + finalRemainingAmount)
                .sumOfPurchaseAmount(sumOfPurchaseAmount)
                .dailyDealHistories(dailyDealHistories)
                .build();
    }
}
