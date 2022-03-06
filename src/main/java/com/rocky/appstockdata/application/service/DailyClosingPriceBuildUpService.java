package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.application.port.in.BuildUpService;
import com.rocky.appstockdata.application.port.out.StockDealRepository;
import com.rocky.appstockdata.domain.*;
import com.rocky.appstockdata.domain.dto.BuildUpModificationSourceDTO;
import com.rocky.appstockdata.domain.dto.BuildUpSourceDTO;
import com.rocky.appstockdata.domain.dto.DailyDealRequestDTO;
import com.rocky.appstockdata.exceptions.NoResultDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.rocky.appstockdata.domain.utils.BuildUpUtil.transformDate;
import static com.rocky.appstockdata.domain.utils.DealTrainingUtil.sortDesc;
import static com.rocky.appstockdata.domain.utils.MovingAverageUtil.addMovingAverage;

@Service
@Slf4j
public class DailyClosingPriceBuildUpService implements BuildUpService {
    final StockDealRepository stockDealRepository;

    public DailyClosingPriceBuildUpService(StockDealRepository stockDealRepository) {
        this.stockDealRepository = stockDealRepository;
    }


    @Override
    public BuildUpType getBuildUpType() {
        return BuildUpType.DAILY_CLOSING;
    }

    @Override
    public BuildUp calculateBuildUp(BuildUpSourceDTO buildUpSourceDTO) {
        BuildUpHistoryAggregation buildUpHistoryAggregation = createBuildUpHistoryAggregation();

        List<DailyDeal> dailyDealList = getDailyDeals(buildUpSourceDTO);

        Iterator<DailyDeal> dailyDeals = dailyDealList.iterator();
        while(dailyDeals.hasNext()){
            DailyDeal dailyDeal = dailyDeals.next();

            buildUpHistoryAggregation = buyOnlyForEveryday(buildUpHistoryAggregation, buildUpSourceDTO, dailyDeal);

            //종가매수 마지막날 전량매도
            if(!dailyDeals.hasNext()){
                buildUpHistoryAggregation = buildUpHistoryAggregation.sellAll(buildUpHistoryAggregation, dailyDeal.getClosingPrice());
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

        return addMovingAverage(dailyDealList);
    }

    private BuildUpHistoryAggregation buyOnlyForEveryday(BuildUpHistoryAggregation buildUpHistoryAggregation, BuildUpSourceDTO buildUpSourceDTO, DailyDeal dailyDeal) {
        Long closingPrice = dailyDeal.getClosingPrice();
        List<DailyDealHistory> dailyDealHistories = buildUpHistoryAggregation.getDailyDealHistories();

        long buildupAmount = buildUpSourceDTO.getBuildupAmount() + buildUpHistoryAggregation.getFinalRemainingAmount();
        int purchaseQuantity = (int) Math.floor(buildupAmount / (double) closingPrice);
        long purchaseAmount = closingPrice * purchaseQuantity;

        //매입 평단 수정 : ((기존 평단 * 기존 매수 수량) + (매수가 * 신규 매수 수량)) / (기존 매수 수량 + 신규 매수 수량) ->이동평균 계산법
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
                    .movingAverage(dailyDeal.getMovingAverage())
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
    }

    @Override
    public BuildUp calculateBuildUpModification(BuildUpModificationSourceDTO buildUpModificationSourceDTO) {
        BuildUpHistoryAggregation buildUpHistoryAggregation = createBuildUpHistoryAggregation();

        List<DailyDeal> existingDailyDealList = getExistingDailyDeals(buildUpModificationSourceDTO);

        Iterator<DailyDeal> dailyDeals = existingDailyDealList.iterator();
        while(dailyDeals.hasNext()){
            DailyDeal dailyDeal = dailyDeals.next();

            //아래는 '추가' 매수/매도 관련 로직
            buildUpHistoryAggregation = buildUpHistoryAggregation.initializeSumForToday();
            buildUpHistoryAggregation = buildUpHistoryAggregation.additionalBuyAndSell(buildUpHistoryAggregation, buildUpModificationSourceDTO.getDealModifications(), dailyDeal);

            //아래는 종가매수 관련 로직
            buildUpHistoryAggregation = buyOnlyForEveryday(buildUpHistoryAggregation, buildUpModificationSourceDTO.transformToBuildUpSourceDTO(), dailyDeal);

            //종가매수 마지막날 전량매도
            if(!dailyDeals.hasNext()){
                buildUpHistoryAggregation = buildUpHistoryAggregation.sellAll(buildUpHistoryAggregation, dailyDeal.getClosingPrice());
            }
        }

        return calculateFinalSummary(buildUpHistoryAggregation, existingDailyDealList.get(0).getItemName(), buildUpModificationSourceDTO.getSimulationMode());
    }

    private List<DailyDeal> getExistingDailyDeals(BuildUpModificationSourceDTO buildUpModificationSourceDTO) {
        List<DailyDeal> existingDailyDealList = stockDealRepository.getDailyDeal(DailyDealRequestDTO.builder()
                .companyName(buildUpModificationSourceDTO.getCompanyName())
                .endDate(buildUpModificationSourceDTO.getEndDate())
                .startDate(buildUpModificationSourceDTO.getStartDate())
                .build());
        return existingDailyDealList;
    }
}
