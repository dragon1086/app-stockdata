package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.application.port.in.BuildUpService;
import com.rocky.appstockdata.application.port.out.StockDealRepository;
import com.rocky.appstockdata.domain.*;
import com.rocky.appstockdata.exceptions.NoResultDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.rocky.appstockdata.domain.utils.BuildUpUtil.transformDate;
import static com.rocky.appstockdata.domain.utils.DealTrainingUtil.sortDesc;

@Service
@Slf4j
public class MinusCandleBuildUpService implements BuildUpService {
    final StockDealRepository stockDealRepository;

    public MinusCandleBuildUpService(StockDealRepository stockDealRepository) {
        this.stockDealRepository = stockDealRepository;
    }


    @Override
    public BuildUpType getBuildUpType() {
        return BuildUpType.MINUS_CANDLE;
    }

    @Override
    public BuildUp calculateBuildUp(BuildUpSourceDTO buildUpSourceDTO) {
        BuildUpHistoryAggregation buildUpHistoryAggregation = BuildUpHistoryAggregation.builder()
                .sumOfPurchaseAmount(0L)
                .sumOfSellingAmount(0L)
                .sumOfCommission(0L)
                .sumOfRealizedEarningAmount(0L)
                .sumOfPurchaseQuantity(0)
                .sumOfSellingQuantity(0)
                .sumOfMyQuantity(0)
                .myAverageUnitPrice(0.0d)
                .finalRemainingAmount(0L)
                .sumOfAdditionalBuyingQuantityForToday(0)
                .sumOfAdditionalSellingQuantityForToday(0)
                .sumOfAdditionalBuyingAmountForToday(0L)
                .sumOfAdditionalSellingAmountForToday(0L)
                .sumOfCommissionForToday(0L)
                .sumOfRealizedEarningAmountForToday(0L)
                .dailyDealHistories(new ArrayList<>())
                .build();

        List<DailyDeal> dailyDealList = getDailyDeals(buildUpSourceDTO);

        Iterator<DailyDeal> dailyDeals = dailyDealList.iterator();
        while(dailyDeals.hasNext()){
            DailyDeal dailyDeal = dailyDeals.next();

            Long closingPrice = dailyDeal.getClosingPrice();

            //TODO: 리팩토링 여기거 빼끼면 됨
            BuildUpHistoryAggregation aggregationAfterBuyingForMinusCandle = buyOnlyForMinusCandle(buildUpHistoryAggregation, buildUpSourceDTO, dailyDeal);
            //후처리로 합산과정 추가
            buildUpHistoryAggregation.updateAggregations(aggregationAfterBuyingForMinusCandle);

            //종가매수 마지막날 전량매도
            if(!dailyDeals.hasNext()){
                BuildUpHistoryAggregation aggregationAfterAllSell = sellAll(buildUpHistoryAggregation, closingPrice);
                //후처리로 합산과정 추가
                buildUpHistoryAggregation.updateAggregations(aggregationAfterAllSell);
            }
        }

        //수익률 : 실현손익 총합 / (남은금액 + 구매한 총금액)
        double myEarningRate = buildUpHistoryAggregation.getSumOfRealizedEarningAmount() / (double)(buildUpHistoryAggregation.getFinalRemainingAmount() + buildUpHistoryAggregation.getSumOfPurchaseAmount());
        //최종적으로 손에 든 금액 : 남은금액 + 구매한 총금액 + 실현손익
        long myTotalAmount = buildUpHistoryAggregation.getFinalRemainingAmount() + buildUpHistoryAggregation.getSumOfPurchaseAmount() + buildUpHistoryAggregation.getSumOfRealizedEarningAmount();

        return BuildUp.builder()
                .simulationMode(buildUpSourceDTO.getSimulationMode())
                .itemName(dailyDealList.get(0).getItemName())
                .earningRate(Double.parseDouble(String.format("%.2f",myEarningRate * 100)))
                .earningAmount(buildUpHistoryAggregation.getSumOfRealizedEarningAmount())
                .totalAmount(myTotalAmount)
                .sumOfPurchaseAmount(buildUpHistoryAggregation.getSumOfPurchaseAmount())
                .sumOfSellingAmount(buildUpHistoryAggregation.getSumOfSellingAmount())
                .sumOfCommission(buildUpHistoryAggregation.getSumOfCommission())
                .sumOfPurchaseQuantity(buildUpHistoryAggregation.getSumOfPurchaseQuantity())
                .sumOfSellingQuantity(buildUpHistoryAggregation.getSumOfSellingQuantity())
                .dailyDealHistories(buildUpHistoryAggregation.getDailyDealHistories())
                .dailyDealHistoriesDesc(sortDesc(buildUpHistoryAggregation.getDailyDealHistories()))
                .build();
    }

    private List<DailyDeal> getDailyDeals(BuildUpSourceDTO buildUpSourceDTO) {
        List<DailyDeal> dailyDealList = stockDealRepository.getDailyDeal(DailyDealRequestDTO.builder()
                .companyName(buildUpSourceDTO.getCompanyName())
                .startDate(buildUpSourceDTO.getStartDate())
                .endDate(buildUpSourceDTO.getEndDate())
                .build());
        if(dailyDealList.isEmpty()){
            throw new NoResultDataException("조회하신 검색 결과가 없습니다. 종목명 또는 조회기간을 확인 부탁드립니다.\r\n"
                    + "종목명은 영문표기를 한글로 변형 또는 반대로 해서 다시 입력해보시기 바랍니다.");
        }
        return dailyDealList;
    }

    private BuildUpHistoryAggregation buyOnlyForMinusCandle(BuildUpHistoryAggregation buildUpHistoryAggregation, BuildUpSourceDTO buildUpSourceDTO, DailyDeal dailyDeal){
        Long closingPrice = dailyDeal.getClosingPrice();
        List<DailyDealHistory> dailyDealHistories = buildUpHistoryAggregation.getDailyDealHistories();

        //음봉일 때만 매수
        if(closingPrice - dailyDeal.getStartPrice() < 0){
            long buildupAmount = buildUpSourceDTO.getBuildupAmount() + buildUpHistoryAggregation.getFinalRemainingAmount();
            int purchaseQuantity = (int) Math.floor(buildupAmount / (double) closingPrice);
            long purchaseAmount = closingPrice * purchaseQuantity;

            //매입 평단 수정 : ((기존 평단 * 내 보유 수량) + (매수가 * 신규 매수 수량)) / (내 보유수량 + 신규 매수 수량) ->이동평균 계산법
            double myAverageUnitPrice = Math.round(((buildUpHistoryAggregation.getMyAverageUnitPrice() * buildUpHistoryAggregation.getSumOfMyQuantity()) + (closingPrice * purchaseQuantity))
                    / (double)(buildUpHistoryAggregation.getSumOfMyQuantity() + purchaseQuantity));
            //총 구매개수 누적
            int sumOfPurchaseQuantity = buildUpHistoryAggregation.getSumOfPurchaseQuantity() + purchaseQuantity;
            //총 보유수량 누적
            int sumOfMyQuantity = buildUpHistoryAggregation.getSumOfMyQuantity() + purchaseQuantity;
            //총 매수금액 누적
            long sumOfPurchaseAmount = buildUpHistoryAggregation.getSumOfPurchaseAmount() + purchaseAmount;

            if(sumOfPurchaseQuantity != 0L){
                dailyDealHistories.add(DailyDealHistory.builder()
                        .dealDate(dailyDeal.getDealDate())
                        .dealDateForTimestamp(transformDate(dailyDeal.getDealDate()))
                        .closingPrice(closingPrice)
                        .startPrice(dailyDeal.getStartPrice())
                        .highPrice(dailyDeal.getHighPrice())
                        .lowPrice(dailyDeal.getLowPrice())
                        .tradeVolume(dailyDeal.getTradeVolume())
                        .myAverageUnitPrice(Math.round(myAverageUnitPrice))
                        .closingPurchaseQuantity(purchaseQuantity)
                        .remainingAmount(buildupAmount - purchaseAmount)
                        .additionalBuyingQuantity(buildUpHistoryAggregation.getSumOfAdditionalBuyingQuantityForToday())
                        .additionalBuyingAmount(buildUpHistoryAggregation.getSumOfAdditionalBuyingAmountForToday())
                        .additionalSellingQuantity(buildUpHistoryAggregation.getSumOfAdditionalSellingQuantityForToday())
                        .additionalSellingAmount(buildUpHistoryAggregation.getSumOfAdditionalSellingAmountForToday())
                        .commission(buildUpHistoryAggregation.getSumOfCommissionForToday())
                        .realizedEarningAmount(buildUpHistoryAggregation.getSumOfRealizedEarningAmountForToday())
                        .build());
            }

            //남아있는 예수금 갱신
            long finalRemainingAmount = buildupAmount - purchaseAmount;

            return buildUpHistoryAggregation.updateSumForMinusCandleBuildUp(sumOfPurchaseQuantity,
                                                                            sumOfMyQuantity,
                                                                            sumOfPurchaseAmount,
                                                                            myAverageUnitPrice,
                                                                            finalRemainingAmount,
                                                                            dailyDealHistories);
        } else {
            dailyDealHistories.add(DailyDealHistory.builder()
                    .dealDate(dailyDeal.getDealDate())
                    .dealDateForTimestamp(transformDate(dailyDeal.getDealDate()))
                    .closingPrice(closingPrice)
                    .startPrice(dailyDeal.getStartPrice())
                    .highPrice(dailyDeal.getHighPrice())
                    .lowPrice(dailyDeal.getLowPrice())
                    .tradeVolume(dailyDeal.getTradeVolume())
                    .myAverageUnitPrice(Math.round(buildUpHistoryAggregation.getMyAverageUnitPrice()))
                    .closingPurchaseQuantity(0)
                    .remainingAmount(buildUpHistoryAggregation.getFinalRemainingAmount())
                    .additionalBuyingQuantity(buildUpHistoryAggregation.getSumOfAdditionalBuyingQuantityForToday())
                    .additionalBuyingAmount(buildUpHistoryAggregation.getSumOfAdditionalBuyingAmountForToday())
                    .additionalSellingQuantity(buildUpHistoryAggregation.getSumOfAdditionalSellingQuantityForToday())
                    .additionalSellingAmount(buildUpHistoryAggregation.getSumOfAdditionalSellingAmountForToday())
                    .commission(buildUpHistoryAggregation.getSumOfCommissionForToday())
                    .realizedEarningAmount(buildUpHistoryAggregation.getSumOfRealizedEarningAmountForToday())
                    .build());

            return buildUpHistoryAggregation.updateDailyHistories(dailyDealHistories);
        }
    }


    private BuildUpHistoryAggregation sellAll(BuildUpHistoryAggregation buildUpHistoryAggregation, Long closingPrice) {
        //전량매도 수량 : (이전까지 총 구매수량 - 이전까지 총 매도수량)
        int finalSellingQuantity = buildUpHistoryAggregation.getSumOfPurchaseQuantity() - buildUpHistoryAggregation.getSumOfSellingQuantity();
        //전량매도 금액 : 전량매도 수량 * 종가
        long additionalSellingAmount = finalSellingQuantity * closingPrice;
        //매도 수수료(0.3%)
        long commission = Math.round(additionalSellingAmount * 0.003);
        //실현 손익 (전량매도 금액 - 수수료 - (반올림)(현재평단 * 매도개수))
        long realizedEarningAmount = additionalSellingAmount - commission - Math.round(buildUpHistoryAggregation.getMyAverageUnitPrice() * finalSellingQuantity);

        //총 매도개수 누적
        int sumOfSellingQuantity = buildUpHistoryAggregation.getSumOfSellingQuantity() + finalSellingQuantity;
        //총 매도액 누적
        long sumOfSellingAmount = buildUpHistoryAggregation.getSumOfSellingAmount() + additionalSellingAmount;
        //총 수수료 누적
        long sumOfCommission = buildUpHistoryAggregation.getSumOfCommission() + commission;
        //총 실현손익 누적
        long sumOfRealizedEarningAmount = buildUpHistoryAggregation.getSumOfRealizedEarningAmount() + realizedEarningAmount;

        return buildUpHistoryAggregation.updateSellingSumForMinusCandleBuildUp(sumOfSellingQuantity, sumOfSellingAmount, sumOfCommission, sumOfRealizedEarningAmount);
    }

    @Override
    public BuildUp calculateBuildUpModification(BuildUpModificationSourceDTO buildUpModificationSourceDTO) {
        long sumOfPurchaseAmount = 0L;
        long sumOfSellingAmount = 0L;
        long sumOfCommission = 0L;
        long sumOfRealizedEarningAmount = 0L;
        int sumOfPurchaseQuantity = 0;
        int sumOfSellingQuantity = 0;
        int sumOfMyQuantity = 0;
        double myAverageUnitPrice = 0.0d;
        long finalRemainingAmount = 0L;

        List<DailyDealHistory> dailyDealHistories = new ArrayList<>();

        List<DailyDeal> existingDailyDealList = stockDealRepository.getDailyDeal(DailyDealRequestDTO.builder()
                .companyName(buildUpModificationSourceDTO.getCompanyName())
                .endDate(buildUpModificationSourceDTO.getEndDate())
                .startDate(buildUpModificationSourceDTO.getStartDate())
                .build());

        List<DealModification> allDealModifications = buildUpModificationSourceDTO.getDealModifications();

        Iterator<DailyDeal> dailyDeals = existingDailyDealList.iterator();
        while(dailyDeals.hasNext()){
            DailyDeal dailyDeal = dailyDeals.next();

            //TODO: 추가 매수매도 로직, 종가매수 로직 아예 분리시켜놓자.
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
                        int additionalBuyingQuantity = (int) Math.floor((sumOfPurchaseAmount * (double)dealModification.getBuyPercent()/100) / (double) dealModification.getBuyPrice());
                        //매수 금액 : 매수 수량 * 매수 단가
                        long additionalBuyingAmount = additionalBuyingQuantity * dealModification.getBuyPrice();

                        //매입 평단 수정 : ((기존 평단 * 내 보유 수량) + (매수가 * 신규 매수 수량)) / (내 보유수량 + 신규 매수 수량) ->이동평균 계산법
                        // (추가매수와 추가매도가 같은 날에 있을 경우, 추가매도의 평단은 곧 추가매수의 평단이 된다. 왜냐하면 매수 먼저 하니까.)
                        myAverageUnitPrice = Math.round(((myAverageUnitPrice * sumOfMyQuantity) + (dealModification.getBuyPrice() * additionalBuyingQuantity))  / (double)(sumOfMyQuantity + additionalBuyingQuantity));
                        //총 구매개수 누적
                        sumOfPurchaseQuantity += additionalBuyingQuantity;
                        //총 보유수량 누적
                        sumOfMyQuantity += additionalBuyingQuantity;
                        //총 매수금액 누적
                        sumOfPurchaseAmount += additionalBuyingAmount;
                        //당일 추가 매수 수량 합계
                        sumOfAdditionalBuyingQuantityForToday += additionalBuyingQuantity;
                        //당일 추가 매수 금액 합계
                        sumOfAdditionalBuyingAmountForToday += additionalBuyingAmount;
                    }
                    if(dealModification.getSellPercent() != 0 && dealModification.getSellPrice() != 0L){
                        //추가매도 실시. 매도 기록 리스트 형태로 남겨야 함.

                        //매도 수량 : (내림)(이전까지 총 구매금액 * 매도비중 / 매도단가)
                        int additionalSellingQuantity = (int) Math.floor((sumOfPurchaseAmount * (double)dealModification.getSellPercent()/100) / (double) dealModification.getSellPrice());
                        //매도 금액 : 매도 수량 * 매도 단가
                        long additionalSellingAmount = additionalSellingQuantity * dealModification.getSellPrice();
                        //매도 수수료(0.3%)
                        long commission = Math.round(additionalSellingAmount * 0.003);
                        //실현 손익 (매도금액 - 수수료 - (반올림)(현재평단 * 매도개수))
                        long realizedEarningAmount = additionalSellingAmount - commission - Math.round(myAverageUnitPrice * additionalSellingQuantity);

                        //총 매도개수 누적
                        sumOfSellingQuantity += additionalSellingQuantity;
                        //총 보유수량 누적
                        sumOfMyQuantity -= additionalSellingQuantity;
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

            //아래는 음봉일 때만 매수 로직
            if(closingPrice - dailyDeal.getStartPrice() < 0){
                long buildupAmount = buildUpModificationSourceDTO.getBuildupAmount() + finalRemainingAmount;
                int purchaseQuantity = (int) Math.floor(buildupAmount / (double) closingPrice);
                long purchaseAmount = closingPrice * purchaseQuantity;

                //매입 평단 수정 : ((기존 평단 * 내 보유 수량) + (매수가 * 신규 매수 수량)) / (내 보유수량 + 신규 매수 수량) ->이동평균 계산법
                myAverageUnitPrice = Math.round(((myAverageUnitPrice * sumOfMyQuantity) + (closingPrice * purchaseQuantity))  / (double)(sumOfMyQuantity + purchaseQuantity));
                //총 매수수량 누적
                sumOfPurchaseQuantity += purchaseQuantity;
                //총 보유수량 누적
                sumOfMyQuantity += purchaseQuantity;
                //총 매수금액 누적
                sumOfPurchaseAmount += purchaseAmount;

                if(sumOfPurchaseQuantity != 0L){
                    dailyDealHistories.add(DailyDealHistory.builder()
                            .dealDate(dailyDeal.getDealDate())
                            .dealDateForTimestamp(transformDate(dailyDeal.getDealDate()))
                            .closingPrice(closingPrice)
                            .startPrice(dailyDeal.getStartPrice())
                            .highPrice(dailyDeal.getHighPrice())
                            .lowPrice(dailyDeal.getLowPrice())
                            .tradeVolume(dailyDeal.getTradeVolume())
                            .myAverageUnitPrice(Math.round(myAverageUnitPrice))
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

                //남아있는 예수금 갱신
                finalRemainingAmount = buildupAmount - purchaseAmount;
            } else {
                dailyDealHistories.add(DailyDealHistory.builder()
                        .dealDate(dailyDeal.getDealDate())
                        .dealDateForTimestamp(transformDate(dailyDeal.getDealDate()))
                        .closingPrice(closingPrice)
                        .startPrice(dailyDeal.getStartPrice())
                        .highPrice(dailyDeal.getHighPrice())
                        .lowPrice(dailyDeal.getLowPrice())
                        .tradeVolume(dailyDeal.getTradeVolume())
                        .myAverageUnitPrice(Math.round(myAverageUnitPrice))
                        .closingPurchaseQuantity(0)
                        .remainingAmount(finalRemainingAmount)
                        .additionalBuyingQuantity(sumOfAdditionalBuyingQuantityForToday)
                        .additionalBuyingAmount(sumOfAdditionalBuyingAmountForToday)
                        .additionalSellingQuantity(sumOfAdditionalSellingQuantityForToday)
                        .additionalSellingAmount(sumOfAdditionalSellingAmountForToday)
                        .commission(sumOfCommissionForToday)
                        .realizedEarningAmount(sumOfRealizedEarningAmountForToday)
                        .build());
            }


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

        //수익률 : 실현손익 총합 / (남은금액 + 구매한 총금액)
        double myEarningRate = sumOfRealizedEarningAmount / (double)(finalRemainingAmount + sumOfPurchaseAmount);
        //최종적으로 손에 든 금액 : 남은금액 + 구매한 총금액 + 실현손익
        long myTotalAmount = finalRemainingAmount + sumOfPurchaseAmount + sumOfRealizedEarningAmount;

        return BuildUp.builder()
                .simulationMode(buildUpModificationSourceDTO.getSimulationMode())
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
                .dailyDealHistoriesDesc(sortDesc(dailyDealHistories))
                .build();
    }
}
