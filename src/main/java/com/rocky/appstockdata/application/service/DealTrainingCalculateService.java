package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.application.port.in.CompanyNameSearchUseCase;
import com.rocky.appstockdata.application.port.in.DealTrainingUseCase;
import com.rocky.appstockdata.application.port.out.StockDealRepository;
import com.rocky.appstockdata.domain.*;
import com.rocky.appstockdata.domain.dto.DailyDealRequestDTO;
import com.rocky.appstockdata.domain.dto.DealTrainingSourceDTO;
import com.rocky.appstockdata.domain.utils.DealTrainingUtil;
import com.rocky.appstockdata.exceptions.NoResultDataException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.rocky.appstockdata.domain.utils.BuildUpUtil.transformDate;

@Service
@Slf4j
public class DealTrainingCalculateService implements DealTrainingUseCase {
    private final StockDealRepository stockDealRepository;
    private final CompanyNameSearchUseCase companyNameSearchUseCase;

    public DealTrainingCalculateService(StockDealRepository stockDealRepository,
                                        CompanyNameSearchUseCase companyNameSearchUseCase) {
        this.stockDealRepository = stockDealRepository;
        this.companyNameSearchUseCase = companyNameSearchUseCase;
    }

    @Transactional(readOnly = true)
    @Override
    public DealTrainingResult initializeDailyDeal(DealTrainingSourceDTO dealTrainingSourceDTO) {
        DealTrainingSourceDTO requestData = dealTrainingSourceDTO;
        if(StringUtils.isEmpty(dealTrainingSourceDTO.getCompanyName())){
            requestData = dealTrainingSourceDTO.createRandomCompanyName(companyNameSearchUseCase.getRandomCompanyName());
        }

        final int YEAR_CONST = 3;
        LocalDate endDate = createEndDate(requestData);
        LocalDate startDate = endDate.minusYears(YEAR_CONST);


        List<DailyDeal> dailyDealList = getInitializedDailyDealList(requestData, startDate, endDate);
        //마지막 날짜를 빼야 함. 이 마지막날은 사용자가 매수/매도를 입력할 수 있게끔 따로 구성해야 함.
        DailyDeal nextTryDay = dailyDealList.remove(dailyDealList.size() - 1);

        DailyDealHistoryAggregation dailyDealHistoryAggregation = createInitialDailyDealHistoryAggregation(requestData, dailyDealList, nextTryDay);
        addFinalDailyDealForNextTryWith(dailyDealHistoryAggregation, nextTryDay);

        return getInitializedDealTrainingResult(requestData, startDate, nextTryDay, dailyDealHistoryAggregation);
    }

    private LocalDate createEndDate(DealTrainingSourceDTO dealTrainingSourceDTO) {
        //랜덤일자 ~ 그로부터 3년 전
        LocalDate endDate = DealTrainingUtil.getRandomDate(validateAndGetEarliestDate(dealTrainingSourceDTO));
        //시작일자 지정 시 날짜범위는 시작일자 ~ 그로부터 3년 전
        if(StringUtils.isNotEmpty(dealTrainingSourceDTO.getStartDate())){
            endDate = DealTrainingUtil.transformToLocalDateIncludingDash(dealTrainingSourceDTO.getStartDate());
        }
        return endDate;
    }

    private List<DailyDeal> getInitializedDailyDealList(DealTrainingSourceDTO dealTrainingSourceDTO, LocalDate startDate, LocalDate endDate) {
        String endDateString = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String startDateString = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return stockDealRepository.getDailyDeal(DailyDealRequestDTO.builder()
                .companyName(dealTrainingSourceDTO.getCompanyName())
                .startDate(startDateString)
                .endDate(endDateString)
                .build());
    }

    private String validateAndGetEarliestDate(DealTrainingSourceDTO dealTrainingSourceDTO) {
        String earliestDate = stockDealRepository.getEarliestDate(dealTrainingSourceDTO.getCompanyName());

        if(StringUtils.isEmpty(earliestDate)){
            throw new NoResultDataException("조회하신 검색 결과가 없습니다. 종목명을 확인 부탁드립니다.\r\n"
                    + "종목명은 영문표기를 한글로 변형 또는 반대로 해서 다시 입력해보시기 바랍니다.");
        }

        return earliestDate;
    }

    private DailyDealHistoryAggregation createInitialDailyDealHistoryAggregation(DealTrainingSourceDTO dealTrainingSourceDTO, List<DailyDeal> dailyDealList, DailyDeal nextTryDay) {
        long sumOfPurchaseAmount = 0;
        int sumOfPurchaseQuantity = 0;
        long remainingSlotAmount = dealTrainingSourceDTO.getSlotAmount();
        double remainingPortion = 100;
        long myAverageUnitPrice = 0L;
        long finalClosingPrice = 0L;
        int sumOfMyQuantity = 0;
        String initialDealDate = "";
        List<DailyDealHistory> dailyDealHistories = new ArrayList<>();

        Iterator<DailyDeal> dailyDeals = dailyDealList.iterator();
        while(dailyDeals.hasNext()){
            DailyDeal dailyDeal = dailyDeals.next();

            dailyDealHistories.add(DailyDealHistory.builder()
                    .dealDate(dailyDeal.getDealDate())
                    .dealDateForTimestamp(transformDate(dailyDeal.getDealDate()))
                    .closingPrice(dailyDeal.getClosingPrice())
                    .startPrice(dailyDeal.getStartPrice())
                    .highPrice(dailyDeal.getHighPrice())
                    .lowPrice(dailyDeal.getLowPrice())
                    .tradeVolume(dailyDeal.getTradeVolume())
                    .myAverageUnitPrice(myAverageUnitPrice)
                    .build());

            if(!dailyDeals.hasNext()){
                initialDealDate = dailyDeal.getDealDate();
                Random random = new Random();
                double valuationPercent = DealTrainingUtil.minMinusPercent(dealTrainingSourceDTO.getLevel()) + (DealTrainingUtil.maxPlusPercent(dealTrainingSourceDTO.getLevel()) - DealTrainingUtil.minMinusPercent(dealTrainingSourceDTO.getLevel())) * random.nextDouble();
                long initialAverageUnitPrice = (dealTrainingSourceDTO.getPortion() == 0) ? 0 : Math.round(dailyDeal.getClosingPrice() * valuationPercent);
                if(dealTrainingSourceDTO.getValuationPercent() != null){
                    valuationPercent = 1 / ((dealTrainingSourceDTO.getValuationPercent()/100) + 1);
                    initialAverageUnitPrice = Math.round(nextTryDay.getClosingPrice() * valuationPercent);
                }

                long initialAmount = Math.round((double)dealTrainingSourceDTO.getSlotAmount() * dealTrainingSourceDTO.getPortion() / 100);
                int initialBuyingQuantity = (int) Math.floor(initialAmount / (double)initialAverageUnitPrice);
                long purchaseAmount = initialAverageUnitPrice * initialBuyingQuantity;

                remainingSlotAmount -= purchaseAmount;
                remainingPortion -= dealTrainingSourceDTO.getPortion();
                sumOfPurchaseAmount += purchaseAmount;
                sumOfPurchaseQuantity += initialBuyingQuantity;
                myAverageUnitPrice = Math.round(((myAverageUnitPrice * sumOfMyQuantity) + (initialAverageUnitPrice * initialBuyingQuantity)) / (double)(sumOfMyQuantity + initialBuyingQuantity));
                sumOfMyQuantity += initialBuyingQuantity;

                finalClosingPrice = dailyDeal.getClosingPrice();

                //기본적으로 매일매일 거래이력을 남겨야 함. 거래를 하든 안하든.
                dailyDealHistories.add(DailyDealHistory.builder()
                        .dealDate(dailyDeal.getDealDate())
                        .dealDateForTimestamp(transformDate(dailyDeal.getDealDate()))
                        .closingPrice(dailyDeal.getClosingPrice())
                        .startPrice(dailyDeal.getStartPrice())
                        .highPrice(dailyDeal.getHighPrice())
                        .lowPrice(dailyDeal.getLowPrice())
                        .tradeVolume(dailyDeal.getTradeVolume())
                        .myAverageUnitPrice(initialAverageUnitPrice)
                        .closingPurchaseQuantity(initialBuyingQuantity)
                        .additionalBuyingQuantity(initialBuyingQuantity)
                        .additionalBuyingAmount(purchaseAmount)
                        .buyPrice(initialAverageUnitPrice)
                        .buyPercent((int)Math.round(dealTrainingSourceDTO.getPortion()))
                        .remainingAmount(remainingSlotAmount)
                        .portion(100.0 - remainingPortion)
                        .build());
            }
        }

        return DailyDealHistoryAggregation.builder()
                .dailyDealHistories(dailyDealHistories)
                .finalClosingPrice(finalClosingPrice)
                .myAverageUnitPrice(myAverageUnitPrice)
                .remainingPortion(remainingPortion)
                .remainingSlotAmount(remainingSlotAmount)
                .sumOfMyQuantity(sumOfMyQuantity)
                .sumOfPurchaseAmount(sumOfPurchaseAmount)
                .sumOfPurchaseQuantity(sumOfPurchaseQuantity)
                .initialDealDate(initialDealDate)
                .build();
    }

    private void addFinalDailyDealForNextTryWith(DailyDealHistoryAggregation dailyDealHistoryAggregation, DailyDeal nextTryDay) {
        //종가 갱신
        dailyDealHistoryAggregation.updateFinalClosingPrice(nextTryDay.getClosingPrice());

        //차트에 마지막 일봉 추가 -> 이걸로 사용자는 살지 말지 판단할 수 있음
        dailyDealHistoryAggregation.addDailyDealHistory(DailyDealHistory.builder()
                .dealDate(nextTryDay.getDealDate())
                .dealDateForTimestamp(transformDate(nextTryDay.getDealDate()))
                .closingPrice(nextTryDay.getClosingPrice())
                .startPrice(nextTryDay.getStartPrice())
                .highPrice(nextTryDay.getHighPrice())
                .lowPrice(nextTryDay.getLowPrice())
                .tradeVolume(nextTryDay.getTradeVolume())
                .myAverageUnitPrice(dailyDealHistoryAggregation.getMyAverageUnitPrice())
                .portion(100.0 - dailyDealHistoryAggregation.getRemainingPortion())
                .build());
    }


    private DealTrainingResult getInitializedDealTrainingResult(DealTrainingSourceDTO dealTrainingSourceDTO, LocalDate startDate, DailyDeal nextTryDay, DailyDealHistoryAggregation dailyDealHistoryAggregation) {
        //현재 평가손익 기록
        double currentValuationPercent = (dailyDealHistoryAggregation.getSumOfPurchaseAmount() !=0) ?
                Math.round((dailyDealHistoryAggregation.getFinalClosingPrice() - dailyDealHistoryAggregation.getMyAverageUnitPrice())/(double) dailyDealHistoryAggregation.getMyAverageUnitPrice()*100*100)/100.0 : 0.0d;
        //평가 금액 : 종가 * 보유한 수량
        long myTotalAmount = dailyDealHistoryAggregation.getFinalClosingPrice() * dailyDealHistoryAggregation.getSumOfPurchaseQuantity();

        //최초 시작시점 결산데이터
        return DealTrainingResult.builder()
                .itemName(dealTrainingSourceDTO.getCompanyName())
                .startDate(startDate)
                .endDate(DealTrainingUtil.transformToLocalDate(nextTryDay.getDealDate()))
                .dailyDealHistories(dailyDealHistoryAggregation.getDailyDealHistories())
                .remainingSlotAmount(dailyDealHistoryAggregation.getRemainingSlotAmount())
                .remainingPortion(dailyDealHistoryAggregation.getRemainingPortion())
                .sumOfPurchaseAmount(dailyDealHistoryAggregation.getSumOfPurchaseAmount())
                .sumOfPurchaseQuantity(dailyDealHistoryAggregation.getSumOfPurchaseQuantity())
                .totalAmount(myTotalAmount)
                .valuationPercent(currentValuationPercent)
                .averageUnitPrice(dailyDealHistoryAggregation.getMyAverageUnitPrice())
                .currentClosingPrice(dailyDealHistoryAggregation.getFinalClosingPrice())
                .nextTryDate(DealTrainingUtil.transformToDateFormat(nextTryDay.getDealDate()))
                .dealModifications(Collections.singletonList(DealModification.builder()
                        .modifyDate(DealTrainingUtil.transformToDateFormat(dailyDealHistoryAggregation.getInitialDealDate()))
                        .buyPercent(String.valueOf(Math.round(dealTrainingSourceDTO.getPortion())))
                        .buyPrice(String.valueOf(dailyDealHistoryAggregation.getMyAverageUnitPrice()))
                        .build()))
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public DealTrainingResult modifyDailyDeal(DealTrainingSourceDTO dealTrainingSourceDTO) {
        List<DailyDeal> finalDailyDealList;
        //jumpDate가 왔을 경우, 기존 dailDealList 뒤에 붙는 증량분
        int deltaCountByJump = 0;
        String existEndDate = dealTrainingSourceDTO.getEndDate();

        if (dealTrainingSourceDTO.getJumpDate() == null) {
            finalDailyDealList = this.getDailyDealsWithOneDayAfter(dealTrainingSourceDTO);
        } else {
            String lastModifiedDateString = dealTrainingSourceDTO.getStartDate();
            if(!dealTrainingSourceDTO.getDealModifications().isEmpty()) {
                lastModifiedDateString = dealTrainingSourceDTO.getDealModifications().get(dealTrainingSourceDTO.getDealModifications().size() - 1).getModifyDate();
            }

            LocalDate lastModifiedDate = DealTrainingUtil.transformToLocalDateIncludingDash(lastModifiedDateString);
            LocalDate jumpDate = DealTrainingUtil.transformToLocalDateIncludingDash(dealTrainingSourceDTO.getJumpDate());

            if(jumpDate.isBefore(lastModifiedDate)) {
                //점프한 날짜가 마지막에 수정한 날짜보다 이전으로 간다면, 에러로 처리하지는 않고 그냥 다음날로 넘어가게끔 한다. (다음에 프론트 얼럿으로 바꿔야지)
                finalDailyDealList = this.getDailyDealsWithOneDayAfter(dealTrainingSourceDTO);
            } else {
                //endDate를 jumpDate로 바꾸고, jumpDate의 하루 더 뒤로 진행한다. (클라이언트에서는 마지막날을 제외하기 때문)
                dealTrainingSourceDTO.setEndDate(dealTrainingSourceDTO.getJumpDate());
                finalDailyDealList = this.getDailyDealsWithOneDayAfter(dealTrainingSourceDTO);

                //jump로 인해 증분된 데이터 개수
                deltaCountByJump = stockDealRepository.countDelta(DailyDealRequestDTO.builder()
                        .companyName(dealTrainingSourceDTO.getCompanyName())
                        .startDate(existEndDate)
                        .endDate(dealTrainingSourceDTO.getJumpDate())
                        .build());
            }
        }

        //마지막 날짜를 빼야 함. 이 마지막날은 사용자가 매수/매도를 입력할 수 있게끔 따로 구성해야 함.
        DailyDeal nextTryDay = finalDailyDealList.remove(finalDailyDealList.size() - 1);

        DailyDealHistoryAggregation dailyDealHistoryAggregation = createModifiedDailyDealHistoryAggregation(dealTrainingSourceDTO, finalDailyDealList);
        addFinalDailyDealForNextTryWith(dailyDealHistoryAggregation, nextTryDay);

        return getModifiedDealTrainingResult(dealTrainingSourceDTO, nextTryDay, dailyDealHistoryAggregation, deltaCountByJump);
    }

    private List<DailyDeal> getDailyDealsWithOneDayAfter(DealTrainingSourceDTO dealTrainingSourceDTO) {
        //하루 뒤로 시도
        String nextDate = stockDealRepository.getNextDate(dealTrainingSourceDTO.getCompanyName(), dealTrainingSourceDTO.getEndDate());
        if (StringUtils.isEmpty(nextDate)) {
            throw new NoResultDataException("다음에 진행할 데이터가 없습니다");
        }
        LocalDate endDate = DealTrainingUtil.transformToLocalDate(nextDate);

        List<DailyDeal> dailyDealList = stockDealRepository.getDailyDeal(DailyDealRequestDTO.builder()
                .companyName(dealTrainingSourceDTO.getCompanyName())
                .startDate(dealTrainingSourceDTO.getStartDate())
                .endDate(endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build());

        return dailyDealList;
    }

    private DailyDealHistoryAggregation createModifiedDailyDealHistoryAggregation(DealTrainingSourceDTO dealTrainingSourceDTO, List<DailyDeal> dailyDealList) {
        long sumOfPurchaseAmount = 0;
        int sumOfPurchaseQuantity = 0;
        long sumOfSellingAmount = 0L;
        int sumOfSellingQuantity = 0;
        long sumOfCommission = 0L;
        long sumOfRealizedEarningAmount = 0L;
        long remainingSlotAmount = dealTrainingSourceDTO.getSlotAmount();
        double remainingPortion = 100.0d;
        long myAverageUnitPrice = 0L;
        int sumOfMyQuantity = 0;

        List<DailyDealHistory> dailyDealHistories = new ArrayList<>();

        for (DailyDeal dailyDeal : dailyDealList) {
            int sumOfAdditionalBuyingQuantityForToday = 0;
            int sumOfAdditionalSellingQuantityForToday = 0;
            long sumOfAdditionalBuyingAmountForToday = 0L;
            long sumOfAdditionalSellingAmountForToday = 0L;
            long sumOfCommissionForToday = 0L;
            long sumOfRealizedEarningAmountForToday = 0L;
            long buyPriceToday = 0L;
            int buyPercentToday = 0;
            long sellPriceToday = 0L;
            int sellPercentToday = 0;

            //아래는 '추가' 매수/매도 관련 로직
            //매도하면 실현수익은 매도액의 0.3%를 제외한다.
            //같은 행에 추가 매수/매도가 같이 있으면, 먼저 매수 후 매도를 원칙으로 삼는다.
            for (DealModification dealModification : dealTrainingSourceDTO.getDealModifications()) {
                if (dailyDeal.getDealDate().equals(dealModification.getModifyDate().replace("-", ""))) {
                    if (dealModification.getBuyPercent() != 0 && dealModification.getBuyPrice() != 0L) {
                        //추가매수 실시. 매수 기록 리스트 형태로 남겨야 함.

                        //매수 수량 : (내림)(슬랏 할당 금액 * 구매비중 / 매수단가)
                        int additionalBuyingQuantity = (int) Math.floor((dealTrainingSourceDTO.getSlotAmount() * (double) dealModification.getBuyPercent() / 100) / (double) dealModification.getBuyPrice());
                        //매수 금액 : 매수 수량 * 매수 단가
                        long additionalBuyingAmount = additionalBuyingQuantity * dealModification.getBuyPrice();

                        //매입 평단 수정 : ((기존 평단 * 내 보유 수량) + (매수가 * 신규 매수 수량)) / (내 보유수량 + 신규 매수 수량) ->이동평균 계산법
                        // (추가매수와 추가매도가 같은 날에 있을 경우, 추가매도의 평단은 곧 추가매수의 평단이 된다. 왜냐하면 매수 먼저 하니까.)
                        myAverageUnitPrice = Math.round(((myAverageUnitPrice * sumOfMyQuantity) + (dealModification.getBuyPrice() * additionalBuyingQuantity)) / (double) (sumOfMyQuantity + additionalBuyingQuantity));
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
                        //당일 매수가 기록
                        buyPriceToday = dealModification.getBuyPrice();
                        //당일 매수비중 기록
                        buyPercentToday = dealModification.getBuyPercent();

                        //남은 할당금액
                        remainingSlotAmount -= additionalBuyingAmount;
                        //남은 비중(소수점 둘째자리까지)
                        remainingPortion -= Math.round(additionalBuyingAmount / (double) dealTrainingSourceDTO.getSlotAmount() * 100 * 100) / 100.0;

                    }
                    if (dealModification.getSellPercent() != 0 && dealModification.getSellPrice() != 0L) {
                        //추가매도 실시. 매도 기록 리스트 형태로 남겨야 함.

                        //매도 수량 : (내림)(슬랏 할당 금액 * 매도비중 / 매도단가)
                        int additionalSellingQuantity = (int) Math.floor((dealTrainingSourceDTO.getSlotAmount() * (double) dealModification.getSellPercent() / 100) / (double) dealModification.getSellPrice());
                        //매도수량이 내가 매입한 수량보다 많으면 안되므로, 보정
                        if ((sumOfMyQuantity - additionalSellingQuantity) < 0) {
                            additionalSellingQuantity = sumOfMyQuantity;
                        }
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
                        //당일 매도가 기록
                        sellPriceToday = dealModification.getSellPrice();
                        //당일 매도비중 기록
                        sellPercentToday = dealModification.getSellPercent();

                        //남은 할당금액
                        remainingSlotAmount += additionalSellingAmount - realizedEarningAmount;
                        //남은 비중(소수점 둘째자리 까지)
                        remainingPortion = Math.round(remainingSlotAmount / (double) dealTrainingSourceDTO.getSlotAmount() * 100 * 100) / 100.0;
                    }
                }
            }

            //기본적으로 매일매일 거래이력을 남겨야 함. 거래를 하든 안하든.
            dailyDealHistories.add(DailyDealHistory.builder()
                    .dealDate(dailyDeal.getDealDate())
                    .dealDateForTimestamp(transformDate(dailyDeal.getDealDate()))
                    .closingPrice(dailyDeal.getClosingPrice())
                    .startPrice(dailyDeal.getStartPrice())
                    .highPrice(dailyDeal.getHighPrice())
                    .lowPrice(dailyDeal.getLowPrice())
                    .tradeVolume(dailyDeal.getTradeVolume())
                    .myAverageUnitPrice(myAverageUnitPrice)
                    .remainingAmount(remainingSlotAmount)
                    .additionalBuyingQuantity(sumOfAdditionalBuyingQuantityForToday)
                    .additionalBuyingAmount(sumOfAdditionalBuyingAmountForToday)
                    .buyPrice(buyPriceToday)
                    .buyPercent(buyPercentToday)
                    .additionalSellingQuantity(sumOfAdditionalSellingQuantityForToday)
                    .additionalSellingAmount(sumOfAdditionalSellingAmountForToday)
                    .sellPrice(sellPriceToday)
                    .sellPercent(sellPercentToday)
                    .commission(sumOfCommissionForToday)
                    .realizedEarningAmount(sumOfRealizedEarningAmountForToday)
                    .portion(100.0 - remainingPortion)
                    .build());
        }

        return DailyDealHistoryAggregation.builder()
                .dailyDealHistories(dailyDealHistories)
                .myAverageUnitPrice(myAverageUnitPrice)
                .remainingPortion(remainingPortion)
                .remainingSlotAmount(remainingSlotAmount)
                .sumOfMyQuantity(sumOfMyQuantity)
                .sumOfPurchaseAmount(sumOfPurchaseAmount)
                .sumOfPurchaseQuantity(sumOfPurchaseQuantity)
                .sumOfSellingAmount(sumOfSellingAmount)
                .sumOfSellingQuantity(sumOfSellingQuantity)
                .sumOfCommission(sumOfCommission)
                .sumOfRealizedEarningAmount(sumOfRealizedEarningAmount)
                .build();
    }


    private DealTrainingResult getModifiedDealTrainingResult(DealTrainingSourceDTO dealTrainingSourceDTO, DailyDeal nextTryDay, DailyDealHistoryAggregation dailyDealHistoryAggregation, int deltaCountByJump) {
        //현재 평가손익 기록
        double currentValuationPercent = (dailyDealHistoryAggregation.getSumOfPurchaseAmount() !=0) ?
                Math.round((dailyDealHistoryAggregation.getFinalClosingPrice() - dailyDealHistoryAggregation.getMyAverageUnitPrice())/(double) dailyDealHistoryAggregation.getMyAverageUnitPrice()*100*100)/100.0 : 0.0d;
        //실현수익률 : 실현손익 총합 / 할당된 슬랏 금액
        double myEarningRate = dailyDealHistoryAggregation.getSumOfRealizedEarningAmount() / (double) dealTrainingSourceDTO.getSlotAmount();
        //평가 금액 : 종가 * 보유한 수량
        long myTotalAmount = dailyDealHistoryAggregation.getFinalClosingPrice() * dailyDealHistoryAggregation.getSumOfMyQuantity();


        //현재시점 결산데이터
        return DealTrainingResult.builder()
                .itemName(dealTrainingSourceDTO.getCompanyName())
                .earningRate(Double.parseDouble(String.format("%.2f",myEarningRate * 100)))
                .earningAmount(dailyDealHistoryAggregation.getSumOfRealizedEarningAmount())
                .startDate(DealTrainingUtil.transformToLocalDateIncludingDash(dealTrainingSourceDTO.getStartDate()))
                .endDate(DealTrainingUtil.transformToLocalDate(nextTryDay.getDealDate()))
                .dailyDealHistories(dailyDealHistoryAggregation.getDailyDealHistories())
                .remainingSlotAmount(dailyDealHistoryAggregation.getRemainingSlotAmount())
                .remainingPortion(dailyDealHistoryAggregation.getRemainingPortion())
                .totalAmount(myTotalAmount)
                .valuationPercent(currentValuationPercent)
                .averageUnitPrice(dailyDealHistoryAggregation.getMyAverageUnitPrice())
                .currentClosingPrice(dailyDealHistoryAggregation.getFinalClosingPrice())
                .nextTryDate(DealTrainingUtil.transformToDateFormat(nextTryDay.getDealDate()))
                .sumOfPurchaseAmount(dailyDealHistoryAggregation.getSumOfPurchaseAmount())
                .sumOfSellingAmount(dailyDealHistoryAggregation.getSumOfSellingAmount())
                .sumOfCommission(dailyDealHistoryAggregation.getSumOfCommission())
                .sumOfPurchaseQuantity(dailyDealHistoryAggregation.getSumOfPurchaseQuantity())
                .sumOfSellingQuantity(dailyDealHistoryAggregation.getSumOfSellingQuantity())
                .dealModifications(dealTrainingSourceDTO.getDealModifications())
                .deltaCountByJump(deltaCountByJump)
                .build();
    }
}
