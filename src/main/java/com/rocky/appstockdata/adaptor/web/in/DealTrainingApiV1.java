package com.rocky.appstockdata.adaptor.web.in;

import com.rocky.appstockdata.application.port.in.DealTrainingUseCase;
import com.rocky.appstockdata.domain.DealModification;
import com.rocky.appstockdata.domain.DealTrainingResult;
import com.rocky.appstockdata.domain.dto.DealTrainingSourceDTO;
import com.rocky.appstockdata.domain.validator.DealTrainingSourceValidator;
import com.rocky.appstockdata.exceptions.DealTrainingSourceException;
import com.rocky.appstockdata.exceptions.NoResultDataException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class DealTrainingApiV1 {
    private final DealTrainingUseCase dealTrainingUseCase;

    public DealTrainingApiV1(DealTrainingUseCase dealTrainingUseCase) {
        this.dealTrainingUseCase = dealTrainingUseCase;
    }

    @GetMapping("/")
    public String dealTrainingMain(){
        return "index";
    }

    @PostMapping("/deal-calculate")
    public String dealCalculate(ModelMap modelMap,
                                @RequestParam(value = "companyName", required = false) String companyName,
                                @RequestParam(value = "slotAmount", required = false) String slotAmount,
                                @RequestParam(value = "portion", required = false) String portion,
                                @RequestParam(value = "startDate", required = false) String startDate,
                                @RequestParam(value = "valuationPercent", required = false) String valuationPercent,
                                @RequestParam(value = "level", required = false) String level){
        try{
            DealTrainingSourceDTO dealTrainingSourceDTO = DealTrainingSourceDTO.builder()
                    .companyName(companyName)
                    .slotAmount(slotAmount)
                    .portion(portion)
                    .startDate(startDate)
                    .valuationPercent(valuationPercent)
                    .level(StringUtils.isEmpty(level) ? null : level)
                    .build();

            DealTrainingSourceValidator.validate(dealTrainingSourceDTO);

            DealTrainingResult dealTrainingResult = dealTrainingUseCase.initializeDailyDeal(dealTrainingSourceDTO);
            setModelMap(modelMap, dealTrainingResult, dealTrainingSourceDTO);
        } catch (DealTrainingSourceException | NoResultDataException e) {
            return createModelMapWithDealTrainingSourceException(modelMap, e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return createModelMapWithDealTrainingSourceException(modelMap, "서버 오류 발생");
        }

        return "dealTraining";
    }

    private void setModelMap(ModelMap modelMap, DealTrainingResult dealTrainingResult, DealTrainingSourceDTO dealTrainingSourceDTO) {
        modelMap.put("companyName", dealTrainingSourceDTO.getCompanyName());
        modelMap.put("startDate", dealTrainingResult.getStartDate());
        modelMap.put("endDate", dealTrainingResult.getEndDate());
        modelMap.put("itemName", dealTrainingResult.getItemName());
        modelMap.put("dailyDealHistories", dealTrainingResult.getDailyDealHistories());
        modelMap.put("dailyDealHistoriesDesc", dealTrainingResult.getDailyDealHistoriesDesc());
        modelMap.put("initialPortion", dealTrainingSourceDTO.getPortion());
        modelMap.put("slotAmount", dealTrainingSourceDTO.getSlotAmount());
        modelMap.put("portion", 100.0 - dealTrainingResult.getRemainingPortion());
        modelMap.put("remainingSlotAmount", dealTrainingResult.getRemainingSlotAmount());
        modelMap.put("remainingPortion", dealTrainingResult.getRemainingPortion());
        modelMap.put("dealModifications", dealTrainingResult.getDealModifications());
        modelMap.put("totalAmount", dealTrainingResult.getTotalAmount());
        modelMap.put("valuationPercent", dealTrainingResult.getValuationPercent());
        modelMap.put("averageUnitPrice", dealTrainingResult.getAverageUnitPrice());
        modelMap.put("currentClosingPrice", dealTrainingResult.getCurrentClosingPrice());
        modelMap.put("nextTryDate", dealTrainingResult.getNextTryDate());
        modelMap.put("sumOfPurchaseAmount", dealTrainingResult.getSumOfPurchaseAmount());
        modelMap.put("sumOfSellingAmount", dealTrainingResult.getSumOfSellingAmount());
        modelMap.put("sumOfCommission", dealTrainingResult.getSumOfCommission());
        modelMap.put("sumOfPurchaseQuantity", dealTrainingResult.getSumOfPurchaseQuantity());
        modelMap.put("sumOfSellingQuantity", dealTrainingResult.getSumOfSellingQuantity());
        modelMap.put("earningRate", dealTrainingResult.getEarningRate());
        modelMap.put("earningAmount", dealTrainingResult.getEarningAmount());
        modelMap.put("isError", "false");
    }

    private String createModelMapWithDealTrainingSourceException(ModelMap modelMap, String message) {
        modelMap.put("isError", "true");
        modelMap.put("errorMessage", message);
        return "dealTraining";
    }

    @PostMapping("/deal-calculate-modify")
    public String dealCalculateModify(ModelMap modelMap,
                                      @RequestParam(value = "companyName") String companyName,
                                      @RequestParam("startDate") String startDate,
                                      @RequestParam("endDate") String endDate,
                                      @RequestParam(value = "slotAmount") String slotAmount,
                                      @RequestParam(value = "portion") String portion,
                                      @RequestParam("modifyDate") String[] modifyDates,
                                      @RequestParam(value = "sellPercent", defaultValue = "0") String[] sellPercents,
                                      @RequestParam(value = "sellPrice", defaultValue = "0") String[] sellPrices,
                                      @RequestParam(value = "buyPercent", defaultValue = "0") String[] buyPercents,
                                      @RequestParam(value = "buyPrice", defaultValue = "0") String[] buyPrices){

        try{
            DealTrainingSourceDTO dealTrainingSourceDTO = DealTrainingSourceDTO.builder()
                    .companyName(companyName)
                    .startDate(startDate)
                    .endDate(endDate)
                    .slotAmount(slotAmount)
                    .portion(portion)
                    .dealModifications(createDealModifications(modifyDates, sellPercents, sellPrices, buyPercents, buyPrices))
                    .build();

            DealTrainingSourceValidator.validate(dealTrainingSourceDTO);

            DealTrainingResult dealTrainingResult = dealTrainingUseCase.modifyDailyDeal(dealTrainingSourceDTO);
            setModelMap(modelMap, dealTrainingResult, dealTrainingSourceDTO);

        } catch (NumberFormatException e){
            return createModelMapWithFail(modelMap, "올바른 데이터 입력 형식이 아닙니다. 뒤로 돌아가서 정확한 형식으로 넣어주세요.", "buildUpResultWithModification");
        } catch (Exception e){
            log.error("서버 오류 발생하였습니다. : {}", e.getMessage());
            e.printStackTrace();
            return createModelMapWithFail(modelMap, "서버 오류 발생하였습니다.", "buildUpResultWithModification");
        }

        return "dealTraining";
    }

    private List<DealModification> createDealModifications(String[] modifyDates, String[] sellPercents, String[] sellPrices, String[] buyPercents, String[] buyPrices) {
        List<DealModification> dealModifications = new ArrayList<>();
        for(int i = 0; i < modifyDates.length; i++){
            if(StringUtils.isEmpty(modifyDates[i])){
                continue;
            }

            dealModifications.add(DealModification.builder()
                    .modifyDate(modifyDates[i])
                    .buyPercent(buyPercents[i])
                    .buyPrice(buyPrices[i])
                    .sellPercent(sellPercents[i])
                    .sellPrice(sellPrices[i])
                    .build());
        }
        return dealModifications;
    }

    @GetMapping("/dealTrainingManual")
    public String dealTrainingManual(){
        return "dealTrainingManual";
    }

    private String createModelMapWithFail(ModelMap modelMap, String message, String viewFileName) {
        modelMap.put("isError", "true");
        modelMap.put("errorMessage", message);
        return viewFileName;
    }
}
