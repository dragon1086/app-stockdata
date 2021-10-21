package com.rocky.appstockdata.application;

import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.BuildUpSourceDTO;
import com.rocky.appstockdata.domain.DailyDeal;
import com.rocky.appstockdata.exceptions.NoResultDataException;
import com.rocky.appstockdata.port.in.BuildUpCalculatePort;
import com.rocky.appstockdata.port.out.StockDealRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildUpCalculateUseCase implements BuildUpCalculatePort {
    StockDealRepository stockDealRepository;
    public BuildUpCalculateUseCase(StockDealRepository stockDealRepository) {
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

        List<DailyDeal> dailyDealList = stockDealRepository.getDailyDeal(buildUpSourceDTO);
        if(dailyDealList.isEmpty()){
            throw new NoResultDataException("조회하신 검색 결과가 없습니다. 종목명 또는 조회기간을 확인 부탁드립니다.\r\n"
                + "종목명은 영문표기를 한글로 변형 또는 반대로 해서 다시 입력해보시기 바랍니다. (종목기준 : 대신증권)");
        }

        for(DailyDeal dailyDeal : dailyDealList){
            Long closingPrice = dailyDeal.getClosingPrice();
            long buildupAmount = buildUpSourceDTO.getBuildupAmount() + finalRemainingAmount;
            int purchaseQuantity = (int) Math.floor(buildupAmount / (double) closingPrice);
            long purchaseAmount = closingPrice * purchaseQuantity;

            sumOfPurchaseQuantity += purchaseQuantity;
            sumOfPurchaseAmount += purchaseAmount;

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
                .build();
    }
}
