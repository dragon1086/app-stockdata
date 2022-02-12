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
        BuildUpHistoryAggregation buildUpHistoryAggregation = createBuildUpHistoryAggregation();

        List<DailyDeal> dailyDealList = getDailyDeals(buildUpSourceDTO);

        Iterator<DailyDeal> dailyDeals = dailyDealList.iterator();
        while(dailyDeals.hasNext()){
            DailyDeal dailyDeal = dailyDeals.next();

            buildUpHistoryAggregation = buyOnlyForMinusCandle(buildUpHistoryAggregation, buildUpSourceDTO, dailyDeal);

            //종가매수 마지막날 전량매도
            if(!dailyDeals.hasNext()){
                buildUpHistoryAggregation = sellAll(buildUpHistoryAggregation, dailyDeal.getClosingPrice());
            }
        }
        return calculateFinalSummary(buildUpHistoryAggregation, dailyDealList.get(0).getItemName(), buildUpSourceDTO.getSimulationMode());

    }

    private BuildUpHistoryAggregation createBuildUpHistoryAggregation() {
        return BuildUpHistoryAggregation.builder()
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
    }

    private BuildUp calculateFinalSummary(BuildUpHistoryAggregation buildUpHistoryAggregation, String itemName, String simulationMode) {
        //수익률 : 실현손익 총합 / (남은금액 + 구매한 총금액)
        double myEarningRate = buildUpHistoryAggregation.getSumOfRealizedEarningAmount() / (double) (buildUpHistoryAggregation.getFinalRemainingAmount() + buildUpHistoryAggregation.getSumOfPurchaseAmount());
        //최종적으로 손에 든 금액 : 남은금액 + 구매한 총금액 + 실현손익
        long myTotalAmount = buildUpHistoryAggregation.getFinalRemainingAmount() + buildUpHistoryAggregation.getSumOfPurchaseAmount() + buildUpHistoryAggregation.getSumOfRealizedEarningAmount();

        return BuildUp.builder()
                .simulationMode(simulationMode)
                .itemName(itemName)
                .earningRate(Double.parseDouble(String.format("%.2f", myEarningRate * 100)))
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

            return buildUpHistoryAggregation.updateSum(sumOfPurchaseQuantity,
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

        return buildUpHistoryAggregation.updateSellingSum(sumOfSellingQuantity, sumOfSellingAmount, sumOfCommission, sumOfRealizedEarningAmount);
    }

    @Override
    public BuildUp calculateBuildUpModification(BuildUpModificationSourceDTO buildUpModificationSourceDTO) {
        BuildUpHistoryAggregation buildUpHistoryAggregation = createBuildUpHistoryAggregation();

        List<DailyDeal> existingDailyDealList = getExistingDailyDeals(buildUpModificationSourceDTO);

        List<DealModification> allDealModifications = buildUpModificationSourceDTO.getDealModifications();

        Iterator<DailyDeal> dailyDeals = existingDailyDealList.iterator();
        while(dailyDeals.hasNext()){
            DailyDeal dailyDeal = dailyDeals.next();

            //아래는 '추가' 매수/매도 관련 로직
            //당일에 여러개의 추가매수가 있으면 매수 하고, 추가매도가 있으면 매도 하면 됨. 매도하면 실현수익은 매도액의 0.3%를 제외한다.
            //같은 행에 추가 매수/매도가 같이 있으면, 먼저 매수 후 매도를 원칙으로 삼는다.
            buildUpHistoryAggregation = buildUpHistoryAggregation.initializeSumForToday();
            buildUpHistoryAggregation = additionalBuyAndSell(buildUpHistoryAggregation, allDealModifications, dailyDeal);

            //아래는 음봉일 때만 매수 로직
            buildUpHistoryAggregation = buyOnlyForMinusCandle(buildUpHistoryAggregation, buildUpModificationSourceDTO.transformToBuildUpSourceDTO(), dailyDeal);

            //종가매수 마지막날 전량매도
            if(!dailyDeals.hasNext()){
                buildUpHistoryAggregation = sellAll(buildUpHistoryAggregation, dailyDeal.getClosingPrice());
            }
        }

        //수익률 : 실현손익 총합 / (남은금액 + 구매한 총금액)
        return calculateFinalSummary(buildUpHistoryAggregation, existingDailyDealList.get(0).getItemName(), buildUpModificationSourceDTO.getSimulationMode());
    }

    private BuildUpHistoryAggregation additionalBuyAndSell(BuildUpHistoryAggregation buildUpHistoryAggregation, List<DealModification> allDealModifications, DailyDeal dailyDeal) {
        BuildUpHistoryAggregation copiedBuildUpHistoryAggregation = buildUpHistoryAggregation.copy();
        for(DealModification dealModification : allDealModifications){
            if(dailyDeal.getDealDate().equals(dealModification.getModifyDate().replace("-", ""))){
                if(dealModification.getBuyPercent() != 0 && dealModification.getBuyPrice() != 0L){
                    //추가매수 실시. 매수 기록 리스트 형태로 남겨야 함.

                    //매수 수량 : (내림)(이전까지 총 구매금액 * 구매비중 / 매수단가)
                    int additionalBuyingQuantity = (int) Math.floor((copiedBuildUpHistoryAggregation.getSumOfPurchaseAmount() * (double)dealModification.getBuyPercent()/100) / (double) dealModification.getBuyPrice());
                    //매수 금액 : 매수 수량 * 매수 단가
                    long additionalBuyingAmount = additionalBuyingQuantity * dealModification.getBuyPrice();

                    //매입 평단 수정 : ((기존 평단 * 내 보유 수량) + (매수가 * 신규 매수 수량)) / (내 보유수량 + 신규 매수 수량) ->이동평균 계산법
                    // (추가매수와 추가매도가 같은 날에 있을 경우, 추가매도의 평단은 곧 추가매수의 평단이 된다. 왜냐하면 매수 먼저 하니까.)
                    double myAverageUnitPrice = Math.round(((copiedBuildUpHistoryAggregation.getMyAverageUnitPrice() * copiedBuildUpHistoryAggregation.getSumOfMyQuantity()) + (dealModification.getBuyPrice() * additionalBuyingQuantity))  / (double)(copiedBuildUpHistoryAggregation.getSumOfMyQuantity() + additionalBuyingQuantity));
                    //총 구매개수 누적
                    int sumOfPurchaseQuantity = copiedBuildUpHistoryAggregation.getSumOfPurchaseQuantity() + additionalBuyingQuantity;
                    //총 보유수량 누적
                    int sumOfMyQuantity = copiedBuildUpHistoryAggregation.getSumOfMyQuantity() + additionalBuyingQuantity;
                    //총 매수금액 누적
                    long sumOfPurchaseAmount = copiedBuildUpHistoryAggregation.getSumOfPurchaseAmount() + additionalBuyingAmount;
                    //당일 추가 매수 수량 합계
                    int sumOfAdditionalBuyingQuantityForToday = additionalBuyingQuantity;
                    //당일 추가 매수 금액 합계
                    long sumOfAdditionalBuyingAmountForToday = additionalBuyingAmount;

                    copiedBuildUpHistoryAggregation = copiedBuildUpHistoryAggregation.updateSumForAdditionalBuy(sumOfPurchaseQuantity,
                                                                                                                sumOfMyQuantity,
                                                                                                                sumOfPurchaseAmount,
                                                                                                                myAverageUnitPrice,
                                                                                                                sumOfAdditionalBuyingQuantityForToday,
                                                                                                                sumOfAdditionalBuyingAmountForToday);
                }
                if(dealModification.getSellPercent() != 0 && dealModification.getSellPrice() != 0L){
                    //추가매도 실시. 매도 기록 리스트 형태로 남겨야 함.

                    //매도 수량 : (내림)(이전까지 총 구매금액 * 매도비중 / 매도단가)
                    int additionalSellingQuantity = (int) Math.floor((copiedBuildUpHistoryAggregation.getSumOfPurchaseAmount() * (double)dealModification.getSellPercent()/100) / (double) dealModification.getSellPrice());
                    //매도 금액 : 매도 수량 * 매도 단가
                    long additionalSellingAmount = additionalSellingQuantity * dealModification.getSellPrice();
                    //매도 수수료(0.3%)
                    long commission = Math.round(additionalSellingAmount * 0.003);
                    //실현 손익 (매도금액 - 수수료 - (반올림)(현재평단 * 매도개수))
                    long realizedEarningAmount = additionalSellingAmount - commission - Math.round(copiedBuildUpHistoryAggregation.getMyAverageUnitPrice() * additionalSellingQuantity);

                    //총 매도개수 누적
                    int sumOfSellingQuantity = copiedBuildUpHistoryAggregation.getSumOfSellingQuantity() + additionalSellingQuantity;
                    //총 보유수량 누적
                    int sumOfMyQuantity = copiedBuildUpHistoryAggregation.getSumOfMyQuantity() - additionalSellingQuantity;
                    //총 매도액 누적
                    long sumOfSellingAmount = copiedBuildUpHistoryAggregation.getSumOfSellingAmount() + additionalSellingAmount;
                    //총 수수료 누적
                    long sumOfCommission = copiedBuildUpHistoryAggregation.getSumOfCommission() + commission;
                    //총 실현손익 누적
                    long sumOfRealizedEarningAmount = copiedBuildUpHistoryAggregation.getSumOfRealizedEarningAmount() + realizedEarningAmount;
                    //당일 추가 매도 수량 합계
                    int sumOfAdditionalSellingQuantityForToday = additionalSellingQuantity;
                    //당일 추가 매도 금액 합계
                    long sumOfAdditionalSellingAmountForToday = additionalSellingAmount;
                    //당일 수수료 누적
                    long sumOfCommissionForToday = commission;
                    //당일 실현손익 누적
                    long sumOfRealizedEarningAmountForToday = realizedEarningAmount;

                    copiedBuildUpHistoryAggregation = copiedBuildUpHistoryAggregation.updateSumForAdditionalSell(sumOfSellingQuantity,
                                                                                                                sumOfMyQuantity,
                                                                                                                sumOfSellingAmount,
                                                                                                                sumOfCommission,
                                                                                                                sumOfRealizedEarningAmount,
                                                                                                                sumOfAdditionalSellingQuantityForToday,
                                                                                                                sumOfAdditionalSellingAmountForToday,
                                                                                                                sumOfCommissionForToday,
                                                                                                                sumOfRealizedEarningAmountForToday);
                }
            }
        }

        return copiedBuildUpHistoryAggregation;
    }

    private List<DailyDeal> getExistingDailyDeals(BuildUpModificationSourceDTO buildUpModificationSourceDTO) {
        return stockDealRepository.getDailyDeal(DailyDealRequestDTO.builder()
                .companyName(buildUpModificationSourceDTO.getCompanyName())
                .endDate(buildUpModificationSourceDTO.getEndDate())
                .startDate(buildUpModificationSourceDTO.getStartDate())
                .build());
    }
}
