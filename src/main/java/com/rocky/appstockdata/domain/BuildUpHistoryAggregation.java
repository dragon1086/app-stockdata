package com.rocky.appstockdata.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class BuildUpHistoryAggregation {
    private long sumOfPurchaseAmount;
    private long sumOfSellingAmount;
    private long sumOfCommission;
    private long sumOfRealizedEarningAmount;
    private int sumOfPurchaseQuantity;
    private int sumOfSellingQuantity;
    private int sumOfMyQuantity;
    private double myAverageUnitPrice;
    private long finalRemainingAmount;
    private int sumOfAdditionalBuyingQuantityForToday;
    private int sumOfAdditionalSellingQuantityForToday;
    private long sumOfAdditionalBuyingAmountForToday;
    private long sumOfAdditionalSellingAmountForToday;
    private long sumOfCommissionForToday;
    private long sumOfRealizedEarningAmountForToday;
    private List<DailyDealHistory> dailyDealHistories;
    private Long yesterdayClosingPrice;
    private int countOfDayOnDayClosingPriceIncrease;
    private int countOfDayOnDayClosingPriceDecrease;

    @Builder
    public BuildUpHistoryAggregation(long sumOfPurchaseAmount,
                                     long sumOfSellingAmount,
                                     long sumOfCommission,
                                     long sumOfRealizedEarningAmount,
                                     int sumOfPurchaseQuantity,
                                     int sumOfSellingQuantity,
                                     int sumOfMyQuantity,
                                     double myAverageUnitPrice,
                                     long finalRemainingAmount,
                                     int sumOfAdditionalBuyingQuantityForToday,
                                     int sumOfAdditionalSellingQuantityForToday,
                                     long sumOfAdditionalBuyingAmountForToday,
                                     long sumOfAdditionalSellingAmountForToday,
                                     long sumOfCommissionForToday,
                                     long sumOfRealizedEarningAmountForToday,
                                     List<DailyDealHistory> dailyDealHistories,
                                     Long yesterdayClosingPrice,
                                     int countOfDayOnDayClosingPriceIncrease,
                                     int countOfDayOnDayClosingPriceDecrease) {
        this.sumOfPurchaseAmount = sumOfPurchaseAmount;
        this.sumOfSellingAmount = sumOfSellingAmount;
        this.sumOfCommission = sumOfCommission;
        this.sumOfRealizedEarningAmount = sumOfRealizedEarningAmount;
        this.sumOfPurchaseQuantity = sumOfPurchaseQuantity;
        this.sumOfSellingQuantity = sumOfSellingQuantity;
        this.sumOfMyQuantity = sumOfMyQuantity;
        this.myAverageUnitPrice = myAverageUnitPrice;
        this.finalRemainingAmount = finalRemainingAmount;
        this.sumOfAdditionalBuyingQuantityForToday = sumOfAdditionalBuyingQuantityForToday;
        this.sumOfAdditionalSellingQuantityForToday = sumOfAdditionalSellingQuantityForToday;
        this.sumOfAdditionalBuyingAmountForToday = sumOfAdditionalBuyingAmountForToday;
        this.sumOfAdditionalSellingAmountForToday = sumOfAdditionalSellingAmountForToday;
        this.sumOfCommissionForToday = sumOfCommissionForToday;
        this.sumOfRealizedEarningAmountForToday = sumOfRealizedEarningAmountForToday;
        this.dailyDealHistories = dailyDealHistories;
        this.yesterdayClosingPrice = yesterdayClosingPrice;
        this.countOfDayOnDayClosingPriceIncrease = countOfDayOnDayClosingPriceIncrease;
        this.countOfDayOnDayClosingPriceDecrease = countOfDayOnDayClosingPriceDecrease;
    }

    public static BuildUpHistoryAggregation createBuildUpHistoryAggregation() {
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
                    .yesterdayClosingPrice(0L)
                    .countOfDayOnDayClosingPriceIncrease(0)
                    .countOfDayOnDayClosingPriceDecrease(0)
                    .build();
    }

    public BuildUpHistoryAggregation updateSum(int sumOfPurchaseQuantity,
                                               int sumOfMyQuantity,
                                               long sumOfPurchaseAmount,
                                               double myAverageUnitPrice,
                                               long finalRemainingAmount,
                                               List<DailyDealHistory> dailyDealHistories,
                                               Long closingPrice,
                                               long differenceOfClosingPrice){

        return BuildUpHistoryAggregation.builder()
                .sumOfPurchaseAmount(sumOfPurchaseAmount)
                .sumOfSellingAmount(this.sumOfSellingAmount)
                .sumOfCommission(this.sumOfCommission)
                .sumOfRealizedEarningAmount(this.sumOfRealizedEarningAmount)
                .sumOfPurchaseQuantity(sumOfPurchaseQuantity)
                .sumOfSellingQuantity(this.sumOfSellingQuantity)
                .sumOfMyQuantity(sumOfMyQuantity)
                .myAverageUnitPrice(myAverageUnitPrice)
                .finalRemainingAmount(finalRemainingAmount)
                .sumOfAdditionalBuyingQuantityForToday(this.sumOfAdditionalBuyingQuantityForToday)
                .sumOfAdditionalSellingQuantityForToday(this.sumOfAdditionalSellingQuantityForToday)
                .sumOfAdditionalBuyingAmountForToday(this.sumOfAdditionalBuyingAmountForToday)
                .sumOfAdditionalSellingAmountForToday(this.sumOfAdditionalSellingAmountForToday)
                .sumOfCommissionForToday(this.sumOfCommissionForToday)
                .sumOfRealizedEarningAmountForToday(this.sumOfRealizedEarningAmountForToday)
                .dailyDealHistories(dailyDealHistories)
                //인자로 오는 closingPrice(오늘날짜)를 어제날짜로 치환함
                .yesterdayClosingPrice(closingPrice)
                .countOfDayOnDayClosingPriceIncrease(differenceOfClosingPrice > 0 ? ++this.countOfDayOnDayClosingPriceIncrease : this.countOfDayOnDayClosingPriceIncrease)
                .countOfDayOnDayClosingPriceDecrease(differenceOfClosingPrice < 0 ? ++this.countOfDayOnDayClosingPriceDecrease : this.countOfDayOnDayClosingPriceDecrease)
                .build();
    }

    public BuildUpHistoryAggregation updateSumForAdditionalBuy(int sumOfPurchaseQuantity,
                                                               int sumOfMyQuantity,
                                                               long sumOfPurchaseAmount,
                                                               double myAverageUnitPrice,
                                                               int sumOfAdditionalBuyingQuantityForToday,
                                                               long sumOfAdditionalBuyingAmountForToday){

        return BuildUpHistoryAggregation.builder()
                .sumOfPurchaseAmount(sumOfPurchaseAmount)
                .sumOfSellingAmount(this.sumOfSellingAmount)
                .sumOfCommission(this.sumOfCommission)
                .sumOfRealizedEarningAmount(this.sumOfRealizedEarningAmount)
                .sumOfPurchaseQuantity(sumOfPurchaseQuantity)
                .sumOfSellingQuantity(this.sumOfSellingQuantity)
                .sumOfMyQuantity(sumOfMyQuantity)
                .myAverageUnitPrice(myAverageUnitPrice)
                .finalRemainingAmount(this.finalRemainingAmount)
                .sumOfAdditionalBuyingQuantityForToday(sumOfAdditionalBuyingQuantityForToday)
                .sumOfAdditionalSellingQuantityForToday(this.sumOfAdditionalSellingQuantityForToday)
                .sumOfAdditionalBuyingAmountForToday(sumOfAdditionalBuyingAmountForToday)
                .sumOfAdditionalSellingAmountForToday(this.sumOfAdditionalSellingAmountForToday)
                .sumOfCommissionForToday(this.sumOfCommissionForToday)
                .sumOfRealizedEarningAmountForToday(this.sumOfRealizedEarningAmountForToday)
                .dailyDealHistories(this.dailyDealHistories)
                .yesterdayClosingPrice(this.yesterdayClosingPrice)
                .countOfDayOnDayClosingPriceIncrease(this.countOfDayOnDayClosingPriceIncrease)
                .countOfDayOnDayClosingPriceDecrease(this.countOfDayOnDayClosingPriceDecrease)
                .build();
    }

    public BuildUpHistoryAggregation updateSumForAdditionalSell(int sumOfSellingQuantity,
                                                                int sumOfMyQuantity,
                                                                long sumOfSellingAmount,
                                                                long sumOfCommission,
                                                                long sumOfRealizedEarningAmount,
                                                                int sumOfAdditionalSellingQuantityForToday,
                                                                long sumOfAdditionalSellingAmountForToday,
                                                                long sumOfCommissionForToday,
                                                                long sumOfRealizedEarningAmountForToday){

        return BuildUpHistoryAggregation.builder()
                .sumOfPurchaseAmount(this.sumOfPurchaseAmount)
                .sumOfSellingAmount(sumOfSellingAmount)
                .sumOfCommission(sumOfCommission)
                .sumOfRealizedEarningAmount(sumOfRealizedEarningAmount)
                .sumOfPurchaseQuantity(this.sumOfPurchaseQuantity)
                .sumOfSellingQuantity(sumOfSellingQuantity)
                .sumOfMyQuantity(sumOfMyQuantity)
                .myAverageUnitPrice(this.myAverageUnitPrice)
                .finalRemainingAmount(this.finalRemainingAmount)
                .sumOfAdditionalBuyingQuantityForToday(this.sumOfAdditionalBuyingQuantityForToday)
                .sumOfAdditionalSellingQuantityForToday(sumOfAdditionalSellingQuantityForToday)
                .sumOfAdditionalBuyingAmountForToday(this.sumOfAdditionalBuyingAmountForToday)
                .sumOfAdditionalSellingAmountForToday(sumOfAdditionalSellingAmountForToday)
                .sumOfCommissionForToday(sumOfCommissionForToday)
                .sumOfRealizedEarningAmountForToday(sumOfRealizedEarningAmountForToday)
                .dailyDealHistories(this.dailyDealHistories)
                .yesterdayClosingPrice(this.yesterdayClosingPrice)
                .countOfDayOnDayClosingPriceIncrease(this.countOfDayOnDayClosingPriceIncrease)
                .countOfDayOnDayClosingPriceDecrease(this.countOfDayOnDayClosingPriceDecrease)
                .build();
    }

    public BuildUpHistoryAggregation updateSellingSum(int sumOfSellingQuantity,
                                                      long sumOfSellingAmount,
                                                      long sumOfCommission,
                                                      long sumOfRealizedEarningAmount){

        return BuildUpHistoryAggregation.builder()
                .sumOfPurchaseAmount(this.sumOfPurchaseAmount)
                .sumOfSellingAmount(sumOfSellingAmount)
                .sumOfCommission(sumOfCommission)
                .sumOfRealizedEarningAmount(sumOfRealizedEarningAmount)
                .sumOfPurchaseQuantity(this.sumOfPurchaseQuantity)
                .sumOfSellingQuantity(sumOfSellingQuantity)
                .sumOfMyQuantity(this.sumOfMyQuantity)
                .myAverageUnitPrice(this.myAverageUnitPrice)
                .finalRemainingAmount(this.finalRemainingAmount)
                .sumOfAdditionalBuyingQuantityForToday(this.sumOfAdditionalBuyingQuantityForToday)
                .sumOfAdditionalSellingQuantityForToday(this.sumOfAdditionalSellingQuantityForToday)
                .sumOfAdditionalBuyingAmountForToday(this.sumOfAdditionalBuyingAmountForToday)
                .sumOfAdditionalSellingAmountForToday(this.sumOfAdditionalSellingAmountForToday)
                .sumOfCommissionForToday(this.sumOfCommissionForToday)
                .sumOfRealizedEarningAmountForToday(this.sumOfRealizedEarningAmountForToday)
                .dailyDealHistories(this.dailyDealHistories)
                .yesterdayClosingPrice(this.yesterdayClosingPrice)
                .countOfDayOnDayClosingPriceIncrease(this.countOfDayOnDayClosingPriceIncrease)
                .countOfDayOnDayClosingPriceDecrease(this.countOfDayOnDayClosingPriceDecrease)
                .build();
    }

    public BuildUpHistoryAggregation updateDailyHistories(List<DailyDealHistory> dailyDealHistories) {
        return BuildUpHistoryAggregation.builder()
                .sumOfPurchaseAmount(this.sumOfPurchaseAmount)
                .sumOfSellingAmount(this.sumOfSellingAmount)
                .sumOfCommission(this.sumOfCommission)
                .sumOfRealizedEarningAmount(this.sumOfRealizedEarningAmount)
                .sumOfPurchaseQuantity(this.sumOfPurchaseQuantity)
                .sumOfSellingQuantity(this.sumOfSellingQuantity)
                .sumOfMyQuantity(this.sumOfMyQuantity)
                .myAverageUnitPrice(this.myAverageUnitPrice)
                .finalRemainingAmount(this.finalRemainingAmount)
                .sumOfAdditionalBuyingQuantityForToday(this.sumOfAdditionalBuyingQuantityForToday)
                .sumOfAdditionalSellingQuantityForToday(this.sumOfAdditionalSellingQuantityForToday)
                .sumOfAdditionalBuyingAmountForToday(this.sumOfAdditionalBuyingAmountForToday)
                .sumOfAdditionalSellingAmountForToday(this.sumOfAdditionalSellingAmountForToday)
                .sumOfCommissionForToday(this.sumOfCommissionForToday)
                .sumOfRealizedEarningAmountForToday(this.sumOfRealizedEarningAmountForToday)
                .yesterdayClosingPrice(this.yesterdayClosingPrice)
                .countOfDayOnDayClosingPriceIncrease(this.countOfDayOnDayClosingPriceIncrease)
                .countOfDayOnDayClosingPriceDecrease(this.countOfDayOnDayClosingPriceDecrease)
                .dailyDealHistories(dailyDealHistories)
                .build();
    }

    public BuildUpHistoryAggregation initializeSumForToday() {
        return BuildUpHistoryAggregation.builder()
                .sumOfPurchaseAmount(this.sumOfPurchaseAmount)
                .sumOfSellingAmount(this.sumOfSellingAmount)
                .sumOfCommission(this.sumOfCommission)
                .sumOfRealizedEarningAmount(this.sumOfRealizedEarningAmount)
                .sumOfPurchaseQuantity(this.sumOfPurchaseQuantity)
                .sumOfSellingQuantity(this.sumOfSellingQuantity)
                .sumOfMyQuantity(this.sumOfMyQuantity)
                .myAverageUnitPrice(this.myAverageUnitPrice)
                .finalRemainingAmount(this.finalRemainingAmount)
                .sumOfAdditionalBuyingQuantityForToday(0)
                .sumOfAdditionalSellingQuantityForToday(0)
                .sumOfAdditionalBuyingAmountForToday(0L)
                .sumOfAdditionalSellingAmountForToday(0L)
                .sumOfCommissionForToday(0L)
                .sumOfRealizedEarningAmountForToday(0L)
                .dailyDealHistories(this.dailyDealHistories)
                .yesterdayClosingPrice(this.yesterdayClosingPrice)
                .countOfDayOnDayClosingPriceIncrease(this.countOfDayOnDayClosingPriceIncrease)
                .countOfDayOnDayClosingPriceDecrease(this.countOfDayOnDayClosingPriceDecrease)
                .build();
    }

    public BuildUpHistoryAggregation copy() {
        return BuildUpHistoryAggregation.builder()
                .sumOfPurchaseAmount(this.sumOfPurchaseAmount)
                .sumOfSellingAmount(this.sumOfSellingAmount)
                .sumOfCommission(this.sumOfCommission)
                .sumOfRealizedEarningAmount(this.sumOfRealizedEarningAmount)
                .sumOfPurchaseQuantity(this.sumOfPurchaseQuantity)
                .sumOfSellingQuantity(this.sumOfSellingQuantity)
                .sumOfMyQuantity(this.sumOfMyQuantity)
                .myAverageUnitPrice(this.myAverageUnitPrice)
                .finalRemainingAmount(this.finalRemainingAmount)
                .sumOfAdditionalBuyingQuantityForToday(this.sumOfAdditionalBuyingQuantityForToday)
                .sumOfAdditionalSellingQuantityForToday(this.sumOfAdditionalSellingQuantityForToday)
                .sumOfAdditionalBuyingAmountForToday(this.sumOfAdditionalBuyingAmountForToday)
                .sumOfAdditionalSellingAmountForToday(this.sumOfAdditionalSellingAmountForToday)
                .sumOfCommissionForToday(this.sumOfCommissionForToday)
                .sumOfRealizedEarningAmountForToday(this.sumOfRealizedEarningAmountForToday)
                .yesterdayClosingPrice(this.yesterdayClosingPrice)
                .countOfDayOnDayClosingPriceIncrease(this.countOfDayOnDayClosingPriceIncrease)
                .countOfDayOnDayClosingPriceDecrease(this.countOfDayOnDayClosingPriceDecrease)
                .dailyDealHistories(this.dailyDealHistories)
                .build();
    }


    public BuildUpHistoryAggregation additionalBuyAndSell(BuildUpHistoryAggregation buildUpHistoryAggregation, List<DealModification> allDealModifications, DailyDeal dailyDeal) {
        //당일에 여러개의 추가매수가 있으면 매수 하고, 추가매도가 있으면 매도 하면 됨. 매도하면 실현수익은 매도액의 0.3%를 제외한다.
        //같은 행에 추가 매수/매도가 같이 있으면, 먼저 매수 후 매도를 원칙으로 삼는다.
        BuildUpHistoryAggregation copiedBuildUpHistoryAggregation = buildUpHistoryAggregation.copy();
        for(DealModification dealModification : allDealModifications){
            if(dailyDeal.getDealDate().equals(dealModification.getModifyDate().replace("-", ""))){
                copiedBuildUpHistoryAggregation = additionalBuying(copiedBuildUpHistoryAggregation, dealModification);
                copiedBuildUpHistoryAggregation = additionalSelling(copiedBuildUpHistoryAggregation, dealModification);
            }
        }

        return copiedBuildUpHistoryAggregation;
    }

    private BuildUpHistoryAggregation additionalBuying(BuildUpHistoryAggregation copiedBuildUpHistoryAggregation, DealModification dealModification) {
        if(dealModification.getBuyPercent() != 0 && dealModification.getBuyPrice() != 0L){
            //추가매수 실시. 매수 기록 리스트 형태로 남겨야 함.

            //매수 수량 : (내림)(이전까지 총 구매금액 * 구매비중 / 매수단가)
            int additionalBuyingQuantity = (int) Math.floor((copiedBuildUpHistoryAggregation.getSumOfPurchaseAmount() * (double) dealModification.getBuyPercent()/100) / (double) dealModification.getBuyPrice());
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
        return copiedBuildUpHistoryAggregation;
    }

    private BuildUpHistoryAggregation additionalSelling(BuildUpHistoryAggregation copiedBuildUpHistoryAggregation, DealModification dealModification) {
        if(dealModification.getSellPercent() != 0 && dealModification.getSellPrice() != 0L){
            //추가매도 실시. 매도 기록 리스트 형태로 남겨야 함.

            //매도 수량 : (내림)(이전까지 총 구매금액 * 매도비중 / 매도단가)
            int additionalSellingQuantity = (int) Math.floor((copiedBuildUpHistoryAggregation.getSumOfPurchaseAmount() * (double) dealModification.getSellPercent()/100) / (double) dealModification.getSellPrice());
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
        return copiedBuildUpHistoryAggregation;
    }

    public BuildUpHistoryAggregation sellAll(BuildUpHistoryAggregation buildUpHistoryAggregation, Long closingPrice) {
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

    public BuildUpHistoryAggregation updateCountOfDayOnDayClosingPrice(Long closingPrice, long differenceOfClosingPrice) {
        return BuildUpHistoryAggregation.builder()
                .sumOfPurchaseAmount(this.sumOfPurchaseAmount)
                .sumOfSellingAmount(this.sumOfSellingAmount)
                .sumOfCommission(this.sumOfCommission)
                .sumOfRealizedEarningAmount(this.sumOfRealizedEarningAmount)
                .sumOfPurchaseQuantity(this.sumOfPurchaseQuantity)
                .sumOfSellingQuantity(this.sumOfSellingQuantity)
                .sumOfMyQuantity(this.sumOfMyQuantity)
                .myAverageUnitPrice(this.myAverageUnitPrice)
                .finalRemainingAmount(this.finalRemainingAmount)
                .sumOfAdditionalBuyingQuantityForToday(this.sumOfAdditionalBuyingQuantityForToday)
                .sumOfAdditionalSellingQuantityForToday(this.sumOfAdditionalSellingQuantityForToday)
                .sumOfAdditionalBuyingAmountForToday(this.sumOfAdditionalBuyingAmountForToday)
                .sumOfAdditionalSellingAmountForToday(this.sumOfAdditionalSellingAmountForToday)
                .sumOfCommissionForToday(this.sumOfCommissionForToday)
                .sumOfRealizedEarningAmountForToday(this.sumOfRealizedEarningAmountForToday)
                //인자로 오는 closingPrice(오늘날짜)를 어제날짜로 치환함
                .yesterdayClosingPrice(closingPrice)
                .countOfDayOnDayClosingPriceIncrease(differenceOfClosingPrice > 0 ? ++this.countOfDayOnDayClosingPriceIncrease : this.countOfDayOnDayClosingPriceIncrease)
                .countOfDayOnDayClosingPriceDecrease(differenceOfClosingPrice < 0 ? ++this.countOfDayOnDayClosingPriceDecrease : this.countOfDayOnDayClosingPriceDecrease)
                .dailyDealHistories(this.dailyDealHistories)
                .build();
    }
}
