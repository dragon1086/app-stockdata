package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.application.port.in.BuildUpCalculateUseCase;
import com.rocky.appstockdata.application.port.out.StockDealRepository;
import com.rocky.appstockdata.domain.*;
import com.rocky.appstockdata.exceptions.NoResultDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class BuildUpCalculateService implements BuildUpCalculateUseCase {
    StockDealRepository stockDealRepository;
    public BuildUpCalculateService(StockDealRepository stockDealRepository) {
        this.stockDealRepository = stockDealRepository;
    }


    @Override
    public BuildUp calculateBuildUp(BuildUpSourceDTO buildUpSourceDTO) {
        long sumOfPurchaseAmount = 0L;
        long sumOfSellingAmount = 0L;
        long sumOfCommission = 0L;
        long sumOfRealizedEarningAmount = 0L;
        int sumOfPurchaseQuantity = 0;
        int sumOfSellingQuantity = 0;
        double myAverageUnitPrice = 0.0d;
        long finalRemainingAmount = 0L;

        List<DailyDealHistory> dailyDealHistories = new ArrayList<>();

        List<DailyDeal> dailyDealList = stockDealRepository.getDailyDeal(buildUpSourceDTO);
        if(dailyDealList.isEmpty()){
            throw new NoResultDataException("조회하신 검색 결과가 없습니다. 종목명 또는 조회기간을 확인 부탁드립니다.\r\n"
                + "종목명은 영문표기를 한글로 변형 또는 반대로 해서 다시 입력해보시기 바랍니다.");
        }

        Iterator<DailyDeal> dailyDeals = dailyDealList.iterator();
        while(dailyDeals.hasNext()){
            DailyDeal dailyDeal = dailyDeals.next();

            Long closingPrice = dailyDeal.getClosingPrice();

            long buildupAmount = buildUpSourceDTO.getBuildupAmount() + finalRemainingAmount;
            int purchaseQuantity = (int) Math.floor(buildupAmount / (double) closingPrice);
            long purchaseAmount = closingPrice * purchaseQuantity;

            //총 구매개수 누적
            sumOfPurchaseQuantity += purchaseQuantity;
            //총 매수금액 누적
            sumOfPurchaseAmount += purchaseAmount;
            //매입평단 수정
            myAverageUnitPrice = sumOfPurchaseAmount / (double)sumOfPurchaseQuantity;

            if(sumOfPurchaseQuantity != 0L){
                dailyDealHistories.add(DailyDealHistory.builder()
                        .dealDate(dailyDeal.getDealDate())
                        .dealDateForTimestamp(transformDate(dailyDeal.getDealDate()))
                        .closingPrice(closingPrice)
                        .startPrice(dailyDeal.getStartPrice())
                        .highPrice(dailyDeal.getHighPrice())
                        .lowPrice(dailyDeal.getLowPrice())
                        .tradeVolume(dailyDeal.getTradeVolume())
                        .myAverageUnitPrice(Math.round(sumOfPurchaseAmount / (double)sumOfPurchaseQuantity))
                        .closingPurchaseQuantity(purchaseQuantity)
                        .remainingAmount(buildupAmount - purchaseAmount)
                        .build());
            }

            finalRemainingAmount = buildupAmount - purchaseAmount;

            //종가매수 마지막날 전량매도
            if(!dailyDeals.hasNext()){
                //전량매도 수량 : (이전까지 총 구매수량 - 이전까지 총 매도수량)
                int finalSellingQuantity = sumOfPurchaseQuantity - sumOfSellingQuantity;
                //전량매도 금액 : 전량매도 수량 * 종가
                long additionalSellingAmount = finalSellingQuantity * closingPrice;
                //매도 수수료(0.3%)
                long commission = Math.round(additionalSellingAmount * 0.003);
                //실현 손익 (전량매도 금액 - 수수료 - (반올림)(현재평단 * 매도개수))
                long realizedEarningAmount = additionalSellingAmount - commission - Math.round(myAverageUnitPrice * finalSellingQuantity);

                //총 매도개수 누적
                sumOfSellingQuantity += finalSellingQuantity;
                //총 매도액 누적
                sumOfSellingAmount += additionalSellingAmount;
                //총 수수료 누적
                sumOfCommission += commission;
                //총 실현손익 누적
                sumOfRealizedEarningAmount += realizedEarningAmount;
            }
        }

        //수익률 : (총 매도금액-총 수수료-총 구매금액)/총 구매금액
        double myEarningRate = (sumOfSellingAmount - sumOfCommission - sumOfPurchaseAmount) / (double)sumOfPurchaseAmount;
        //최종적으로 손에 든 금액 : 남은금액 + 구매한 총금액 + 실현손익
        long myTotalAmount = finalRemainingAmount + sumOfPurchaseAmount + sumOfRealizedEarningAmount;

        return BuildUp.builder()
                .itemName(dailyDealList.get(0).getItemName())
                .earningRate(Double.parseDouble(String.format("%.2f",myEarningRate * 100)))
                .earningAmount(sumOfRealizedEarningAmount)
                .totalAmount(myTotalAmount)
                .sumOfPurchaseAmount(sumOfPurchaseAmount)
                .sumOfSellingAmount(sumOfSellingAmount)
                .sumOfCommission(sumOfCommission)
                .sumOfPurchaseQuantity(sumOfPurchaseQuantity)
                .sumOfSellingQuantity(sumOfSellingQuantity)
                .dailyDealHistories(dailyDealHistories)
                .build();
    }

    private long transformDate(String dealDate) {
        int year = Integer.parseInt(dealDate.substring(0,4));
        int month = Integer.parseInt(dealDate.substring(4,6));
        int day = Integer.parseInt(dealDate.substring(6,8));

        return ZonedDateTime.of(year, month, day, 0, 0, 0, 0, ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
    }

    @Override
    public BuildUp calculateBuildUpModification(BuildUpModificationSourceDTO buildUpModificationSourceDTO) {
        long sumOfPurchaseAmount = 0L;
        long sumOfSellingAmount = 0L;
        long sumOfCommission = 0L;
        long sumOfRealizedEarningAmount = 0L;
        int sumOfPurchaseQuantity = 0;
        int sumOfSellingQuantity = 0;
        double myAverageUnitPrice = 0.0d;
        long finalRemainingAmount = 0L;

        List<DailyDealHistory> dailyDealHistories = new ArrayList<>();

        List<DailyDeal> existingDailyDealList = stockDealRepository.getDailyDeal(BuildUpSourceDTO.builder()
                                                                            .companyName(buildUpModificationSourceDTO.getCompanyName())
                                                                            .endDate(buildUpModificationSourceDTO.getEndDate())
                                                                            .startDate(buildUpModificationSourceDTO.getStartDate())
                                                                            .buildupAmount(buildUpModificationSourceDTO.getBuildupAmount())
                .build());

        List<DealModification> allDealModifications = buildUpModificationSourceDTO.getDealModifications();

        Iterator<DailyDeal> dailyDeals = existingDailyDealList.iterator();
        while(dailyDeals.hasNext()){
            DailyDeal dailyDeal = dailyDeals.next();

            Long closingPrice = dailyDeal.getClosingPrice();
            int sumOfAdditionalBuyingQuantityForToday = 0;
            int sumOfAdditionalSellingQuantityForToday = 0;
            long sumOfAdditionalBuyingAmountForToday = 0L;
            long sumOfAdditionalSellingAmountForToday = 0L;
            long sumOfCommissionForToday = 0L;
            long sumOfRealizedEarningAmountForToday = 0L;

            //아래는 '추가' 매수/매도 관련 로직
            //당일에 여러개의 추가매수가 있으면 매수 하고, 추가매도가 있으면 매도 하면 됨. 매도하면 실현수익은 매도액의 0.3%를 제외한다.
            //같은 행에 추가 매수/매도가 같이 있으면, 먼저 매수 후 매도를 원칙으로 삼는다.
            for(DealModification dealModification : allDealModifications){
                if(dailyDeal.getDealDate().equals(dealModification.getModifyDate().replace("-", ""))){
                    if(dealModification.getBuyPercent() != 0 && dealModification.getBuyPrice() != 0L){
                        //추가매수 실시. 매수 기록 리스트 형태로 남겨야 함.

                        //매수 수량 : (내림)(이전까지 총 구매금액 * 구매비중 / 매수단가)
                        int additionalBuyingQuantity = (int) Math.floor((sumOfPurchaseAmount * dealModification.getBuyPercent()/100) / (double) dealModification.getBuyPrice());
                        //매수 금액 : 매수 수량 * 매수 단가
                        long additionalBuyingAmount = additionalBuyingQuantity * dealModification.getBuyPrice();

                        //총 구매개수 누적
                        sumOfPurchaseQuantity += additionalBuyingQuantity;
                        //총 매수금액 누적
                        sumOfPurchaseAmount += additionalBuyingAmount;
                        //매입 평단 수정 (추가매수와 추가매도가 같은 날에 있을 경우, 추가매도의 평단은 곧 추가매수의 평단이 된다. 왜냐하면 매수 먼저 하니까.)
                        myAverageUnitPrice = Math.round(sumOfPurchaseAmount / (double)sumOfPurchaseQuantity);
                        //당일 추가 매수 수량 합계
                        sumOfAdditionalBuyingQuantityForToday += additionalBuyingQuantity;
                        //당일 추가 매수 금액 합계
                        sumOfAdditionalBuyingAmountForToday += additionalBuyingAmount;
                    }
                    if(dealModification.getSellPercent() != 0 && dealModification.getSellPrice() != 0L){
                        //추가매도 실시. 매도 기록 리스트 형태로 남겨야 함.

                        //매도 수량 : (내림)(이전까지 총 구매금액 * 매도비중 / 매도단가)
                        int additionalSellingQuantity = (int) Math.floor((sumOfPurchaseAmount * dealModification.getSellPercent()/100) / (double) dealModification.getSellPrice());
                        //매도 금액 : 매도 수량 * 매도 단가
                        long additionalSellingAmount = additionalSellingQuantity * dealModification.getSellPrice();
                        //매도 수수료(0.3%)
                        long commission = Math.round(additionalSellingAmount * 0.003);
                        //실현 손익 (매도금액 - 수수료 - (반올림)(현재평단 * 매도개수))
                        long realizedEarningAmount = additionalSellingAmount - commission - Math.round(myAverageUnitPrice * additionalSellingQuantity);

                        //총 매도개수 누적
                        sumOfSellingQuantity += additionalSellingQuantity;
                        //총 매도액 누적
                        sumOfSellingAmount += additionalSellingAmount;
                        //총 수수료 누적
                        sumOfCommission += commission;
                        //총 실현손익 누적
                        sumOfRealizedEarningAmount += realizedEarningAmount;
                        //당일 추가 매도 수량 합계
                        sumOfAdditionalSellingQuantityForToday += additionalSellingQuantity;
                        //당일 추가 매도 금액 합계
                        sumOfAdditionalSellingAmountForToday += additionalSellingAmount;
                        //당일 수수료 누적
                        sumOfCommissionForToday += commission;
                        //당일 실현손익 누적
                        sumOfRealizedEarningAmountForToday += realizedEarningAmount;
                    }
                }
            }

            //아래는 종가매수 관련 로직
            long buildupAmount = buildUpModificationSourceDTO.getBuildupAmount() + finalRemainingAmount;
            int purchaseQuantity = (int) Math.floor(buildupAmount / (double) closingPrice);
            long purchaseAmount = closingPrice * purchaseQuantity;

            sumOfPurchaseQuantity += purchaseQuantity;
            sumOfPurchaseAmount += purchaseAmount;
            myAverageUnitPrice = Math.round(sumOfPurchaseAmount / (double)sumOfPurchaseQuantity);

            if(sumOfPurchaseQuantity != 0L){
                dailyDealHistories.add(DailyDealHistory.builder()
                        .dealDate(dailyDeal.getDealDate())
                        .dealDateForTimestamp(transformDate(dailyDeal.getDealDate()))
                        .closingPrice(closingPrice)
                        .startPrice(dailyDeal.getStartPrice())
                        .highPrice(dailyDeal.getHighPrice())
                        .lowPrice(dailyDeal.getLowPrice())
                        .tradeVolume(dailyDeal.getTradeVolume())
                        .myAverageUnitPrice((long)myAverageUnitPrice)
                        .closingPurchaseQuantity(purchaseQuantity)
                        .remainingAmount(buildupAmount - purchaseAmount)
                        .additionalBuyingQuantity(sumOfAdditionalBuyingQuantityForToday)
                        .additionalBuyingAmount(sumOfAdditionalBuyingAmountForToday)
                        .additionalSellingQuantity(sumOfAdditionalSellingQuantityForToday)
                        .additionalSellingAmount(sumOfAdditionalSellingAmountForToday)
                        .commission(sumOfCommissionForToday)
                        .realizedEarningAmount(sumOfRealizedEarningAmountForToday)
                        .build());
            }

            finalRemainingAmount = buildupAmount - purchaseAmount;

            //종가매수 마지막날 전량매도
            if(!dailyDeals.hasNext()){
                //전량매도 수량 : (이전까지 총 구매수량 - 이전까지 총 매도수량)
                int finalSellingQuantity = sumOfPurchaseQuantity - sumOfSellingQuantity;
                //전량매도 금액 : 전량매도 수량 * 종가
                long additionalSellingAmount = finalSellingQuantity * closingPrice;
                //매도 수수료(0.3%)
                long commission = Math.round(additionalSellingAmount * 0.003);
                //실현 손익 (전량매도 금액 - 수수료 - (반올림)(현재평단 * 매도개수))
                long realizedEarningAmount = additionalSellingAmount - commission - Math.round(myAverageUnitPrice * finalSellingQuantity);

                //총 매도개수 누적
                sumOfSellingQuantity += finalSellingQuantity;
                //총 매도액 누적
                sumOfSellingAmount += additionalSellingAmount;
                //총 수수료 누적
                sumOfCommission += commission;
                //총 실현손익 누적
                sumOfRealizedEarningAmount += realizedEarningAmount;
            }
        }

        //수익률 : (총 매도금액-총 수수료-총 구매금액)/총 구매금액
        double myEarningRate = (sumOfSellingAmount - sumOfCommission - sumOfPurchaseAmount) / (double)sumOfPurchaseAmount;
        //최종적으로 손에 든 금액 : 남은금액 + 구매한 총금액 + 실현손익
        long myTotalAmount = finalRemainingAmount + sumOfPurchaseAmount + sumOfRealizedEarningAmount;

        return BuildUp.builder()
                .itemName(existingDailyDealList.get(0).getItemName())
                .earningRate(Double.parseDouble(String.format("%.2f",myEarningRate * 100)))
                .earningAmount(sumOfRealizedEarningAmount)
                .totalAmount(myTotalAmount)
                .sumOfPurchaseAmount(sumOfPurchaseAmount)
                .sumOfSellingAmount(sumOfSellingAmount)
                .sumOfCommission(sumOfCommission)
                .sumOfPurchaseQuantity(sumOfPurchaseQuantity)
                .sumOfSellingQuantity(sumOfSellingQuantity)
                .dailyDealHistories(dailyDealHistories)
                .build();
    }
}
