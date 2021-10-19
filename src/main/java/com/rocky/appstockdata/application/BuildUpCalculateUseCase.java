package com.rocky.appstockdata.application;

import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.BuildUpSourceDTO;
import com.rocky.appstockdata.domain.DailyDeal;
import com.rocky.appstockdata.port.in.BuildUpCalculatePort;
import com.rocky.appstockdata.port.out.StockDealRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        for(DailyDeal dailyDeal : dailyDealList){
            Long closingPrice = dailyDeal.getClosingPrice();
            Long buildupAmount = buildUpSourceDTO.getBuildupAmount() + finalRemainingAmount;
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
        long myEvaluatedAmount = sumOfPurchaseAmount + myEarningAmount + finalRemainingAmount;


        return BuildUp.builder()
                .earningRate(Double.parseDouble(String.format("%.2f",myEarningRate * 100)))
                .earningAmount(myEarningAmount)
                .totalAmount(myEvaluatedAmount)
                .build();
    }
}
