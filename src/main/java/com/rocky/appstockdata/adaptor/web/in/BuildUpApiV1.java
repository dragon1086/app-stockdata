package com.rocky.appstockdata.adaptor.web.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocky.appstockdata.application.port.in.BuildUpCalculateUseCase;
import com.rocky.appstockdata.application.port.in.CompanyNameSearchUseCase;
import com.rocky.appstockdata.application.port.in.DealTrainingUseCase;
import com.rocky.appstockdata.domain.*;
import com.rocky.appstockdata.domain.validator.BuildUpSourceValidator;
import com.rocky.appstockdata.domain.validator.DealTrainingSourceValidator;
import com.rocky.appstockdata.exceptions.BuildUpSourceException;
import com.rocky.appstockdata.exceptions.DealTrainingSourceException;
import com.rocky.appstockdata.exceptions.NoResultDataException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class BuildUpApiV1 {
    private final BuildUpCalculateUseCase buildUpCalculateUseCase;
    private final DealTrainingUseCase dealTrainingUseCase;
    private final CompanyNameSearchUseCase companyNameSearchUseCase;

    public BuildUpApiV1(BuildUpCalculateUseCase buildUpCalculateUseCase,
                        DealTrainingUseCase dealTrainingUseCase,
                        CompanyNameSearchUseCase companyNameSearchUseCase) {
        this.buildUpCalculateUseCase = buildUpCalculateUseCase;
        this.dealTrainingUseCase = dealTrainingUseCase;
        this.companyNameSearchUseCase = companyNameSearchUseCase;
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
            return createModelMapWithNumberFormatException(modelMap, "올바른 데이터 입력 형식이 아닙니다. 뒤로 돌아가서 정확한 형식으로 넣어주세요.", "buildUpResultWithModification");
        } catch (Exception e){
            log.error("서버 오류 발생하였습니다. : {}", e.getMessage());
            e.printStackTrace();
            return createModelMapWithException(modelMap, "서버 오류 발생하였습니다.", "buildUpResultWithModification");
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

    @GetMapping("/buildup")
    public String index(){
        return "buildUpIndex";
    }

    @GetMapping("/buildupManual")
    public String buildupManual(){
        return "buildupManual";
    }

    @PostMapping(value = "buildup-calculate")
    public String buildUpResultController(ModelMap modelMap,
                                          @RequestParam(value = "simulationMode", required = false) String simulationMode,
                                          @RequestParam(value = "companyName", required = false) String companyName,
                                          @RequestParam(value = "buildupAmount", required = false) String buildupAmount,
                                          @RequestParam(value = "startDate", required = false) String startDate,
                                          @RequestParam(value = "endDate", required = false) String endDate){

        try{
            BuildUpSourceDTO buildUpSourceDTO = BuildUpSourceDTO.builder()
                    .simulationMode(simulationMode)
                    .companyName(companyName)
                    .buildupAmount(Long.parseLong(buildupAmount))
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();
            BuildUpSourceValidator.validate(buildUpSourceDTO);

            BuildUp buildUp = buildUpCalculateUseCase.calculateBuildUp(buildUpSourceDTO);

            setModelMap(modelMap, buildUp, buildUpSourceDTO);
        } catch (BuildUpSourceException | NoResultDataException e){
            return createModelMapWithBuilUpException(modelMap, e.getMessage());
        } catch (NumberFormatException e){
            return createModelMapWithNumberFormatException(modelMap, "빌드업 금액 입력 시 숫자로 입력하셔야 합니다.", "buildUpResult");
        } catch (Exception e){
            return createModelMapWithException(modelMap, "서버 오류 발생하였습니다.", "buildUpResult");
        }

        return "buildUpResult";
    }

    private void setModelMap(ModelMap modelMap, BuildUp buildUp, BuildUpSourceDTO buildUpSourceDTO) {
        modelMap.put("companyName", buildUpSourceDTO.getCompanyName());
        modelMap.put("buildupAmount", buildUpSourceDTO.getBuildupAmount());
        modelMap.put("startDate", buildUpSourceDTO.getStartDate());
        modelMap.put("endDate", buildUpSourceDTO.getEndDate());
        modelMap.put("itemName", buildUp.getItemName());
        modelMap.put("simulationMode", buildUp.getSimulationMode());
        modelMap.put("earningRate", buildUp.getEarningRate());
        modelMap.put("earningAmount", buildUp.getEarningAmount());
        modelMap.put("totalAmount", buildUp.getTotalAmount());
        modelMap.put("sumOfPurchaseAmount", buildUp.getSumOfPurchaseAmount());
        modelMap.put("sumOfSellingAmount", buildUp.getSumOfSellingAmount());
        modelMap.put("sumOfCommission", buildUp.getSumOfCommission());
        modelMap.put("sumOfPurchaseQuantity", buildUp.getSumOfPurchaseQuantity());
        modelMap.put("sumOfSellingQuantity", buildUp.getSumOfSellingQuantity());
        modelMap.put("dailyDealHistories", buildUp.getDailyDealHistories());
        modelMap.put("dailyDealHistoriesDesc", buildUp.getDailyDealHistoriesDesc());
        modelMap.put("isError", "false");
    }

    @PostMapping(value = "buildup-calculate-modify")
    public String buildUpModificationController(ModelMap modelMap,
                                                @RequestParam("simulationMode") String simulationMode,
                                                @RequestParam("companyName") String inputCompanyName,
                                                @RequestParam("startDate") String startDate,
                                                @RequestParam("endDate") String endDate,
                                                @RequestParam("buildupAmount") String buildupAmount,
                                                @RequestParam("modifyDate") String[] modifyDates,
                                                @RequestParam(value = "sellPercent", defaultValue = "0") String[] sellPercents,
                                                @RequestParam(value = "sellPrice", defaultValue = "0") String[] sellPrices,
                                                @RequestParam(value = "buyPercent", defaultValue = "0") String[] buyPercents,
                                                @RequestParam(value = "buyPrice", defaultValue = "0") String[] buyPrices){

        List<DealModification> dealModifications = new ArrayList<>();

        try{
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


            BuildUpModificationSourceDTO buildUpModificationSourceDTO = BuildUpModificationSourceDTO.builder()
                    .simulationMode(simulationMode)
                    .companyName(inputCompanyName)
                    .startDate(startDate)
                    .endDate(endDate)
                    .buildupAmount(Long.parseLong(buildupAmount))
                    .dealModifications(dealModifications)
                    .build();

            log.info("dealModification :" + dealModifications.toString());

            BuildUp buildUp = buildUpCalculateUseCase.calculateBuildUpModification(buildUpModificationSourceDTO);

            setModificationModelMap(modelMap, buildUp, buildUpModificationSourceDTO);

        } catch (NumberFormatException e){
            return createModelMapWithNumberFormatException(modelMap, "올바른 데이터 입력 형식이 아닙니다. 뒤로 돌아가서 정확한 형식으로 넣어주세요.", "buildUpResultWithModification");
        } catch (BuildUpSourceException e){
            return createModelMapWithException(modelMap, e.getMessage(), "buildUpResultWithModification");
        } catch (Exception e){
            return createModelMapWithException(modelMap, "서버 오류 발생하였습니다.", "buildUpResultWithModification");
        }
        return "buildUpResultWithModification";
    }

    private void setModificationModelMap(ModelMap modelMap, BuildUp buildUp, BuildUpModificationSourceDTO buildUpModificationSourceDTO) {
        modelMap.put("companyName", buildUpModificationSourceDTO.getCompanyName());
        modelMap.put("buildupAmount", buildUpModificationSourceDTO.getBuildupAmount());
        modelMap.put("startDate", buildUpModificationSourceDTO.getStartDate());
        modelMap.put("endDate", buildUpModificationSourceDTO.getEndDate());
        modelMap.put("itemName", buildUp.getItemName());
        modelMap.put("simulationMode", buildUp.getSimulationMode());
        modelMap.put("earningRate", buildUp.getEarningRate());
        modelMap.put("earningAmount", buildUp.getEarningAmount());
        modelMap.put("totalAmount", buildUp.getTotalAmount());
        modelMap.put("sumOfPurchaseAmount", buildUp.getSumOfPurchaseAmount());
        modelMap.put("sumOfSellingAmount", buildUp.getSumOfSellingAmount());
        modelMap.put("sumOfCommission", buildUp.getSumOfCommission());
        modelMap.put("sumOfPurchaseQuantity", buildUp.getSumOfPurchaseQuantity());
        modelMap.put("sumOfSellingQuantity", buildUp.getSumOfSellingQuantity());
        modelMap.put("dailyDealHistories", buildUp.getDailyDealHistories());
        modelMap.put("dailyDealHistoriesDesc", buildUp.getDailyDealHistoriesDesc());
        modelMap.put("dealModifications", buildUpModificationSourceDTO.dealModificationsForView());
        modelMap.put("isError", "false");
    }


    private String createModelMapWithBuilUpException(ModelMap modelMap, String message) {
        modelMap.put("isError", "true");
        modelMap.put("errorMessage", message);
        return "buildUpResult";
    }

    private String createModelMapWithNumberFormatException(ModelMap modelMap, String message, String viewFileName) {
        modelMap.put("isError", "true");
        modelMap.put("errorMessage", message);
        return viewFileName;
    }
    private String createModelMapWithException(ModelMap modelMap, String message, String viewFileName) {
        modelMap.put("isError", "true");
        modelMap.put("errorMessage", message);
        return viewFileName;
    }

    @RequestMapping(value = "/company", method = RequestMethod.POST)
    @ResponseBody
    public String getCompanyNames(@RequestParam(value = "keyword") String keyword) {
        List<String> results = companyNameSearchUseCase.getCompanyNames(keyword);

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(results);
        } catch (JsonProcessingException e) {
            return "검색 결과 없음";
        }

    }
}
