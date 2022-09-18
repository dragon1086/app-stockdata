package com.rocky.appstockdata.adaptor.web.in;

import com.rocky.appstockdata.application.port.in.BuildUpCalculateUseCase;
import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.DealModification;
import com.rocky.appstockdata.domain.dto.BuildUpModificationSourceDTO;
import com.rocky.appstockdata.domain.dto.BuildUpSourceDTO;
import com.rocky.appstockdata.domain.validator.BuildUpSourceValidator;
import com.rocky.appstockdata.exceptions.BuildUpSourceException;
import com.rocky.appstockdata.exceptions.NoResultDataException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BuildUpApiV1 {
    private final BuildUpCalculateUseCase buildUpCalculateUseCase;

    public BuildUpApiV1(BuildUpCalculateUseCase buildUpCalculateUseCase) {
        this.buildUpCalculateUseCase = buildUpCalculateUseCase;
    }

    @GetMapping("/buildup")
    public String buildupIndex(){
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
                    .buildupAmount(buildupAmount)
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();
            BuildUpSourceValidator.validate(buildUpSourceDTO);

            BuildUp buildUp = buildUpCalculateUseCase.calculateBuildUp(buildUpSourceDTO);

            setModelMap(modelMap, buildUp, buildUpSourceDTO);
        } catch (BuildUpSourceException | NoResultDataException e){
            return createModelMapWithBuilUpException(modelMap, e.getMessage());
        } catch (NumberFormatException e){
            return createModelMapWithFail(modelMap, "빌드업 금액 입력 시 숫자로 입력하셔야 합니다.", "buildUpResult");
        } catch (Exception e){
            return createModelMapWithFail(modelMap, "서버 오류 발생하였습니다.", "buildUpResult");
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
        modelMap.put("countOfDayOnDayClosingPriceIncrease", buildUp.getCountOfDayOnDayClosingPriceIncrease());
        modelMap.put("countOfDayOnDayClosingPriceDecrease", buildUp.getCountOfDayOnDayClosingPriceDecrease());
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

            BuildUp buildUp = buildUpCalculateUseCase.calculateBuildUpModification(buildUpModificationSourceDTO);

            setModificationModelMap(modelMap, buildUp, buildUpModificationSourceDTO);

        } catch (NumberFormatException e){
            return createModelMapWithFail(modelMap, "올바른 데이터 입력 형식이 아닙니다. 뒤로 돌아가서 정확한 형식으로 넣어주세요.", "buildUpResultWithModification");
        } catch (BuildUpSourceException e){
            return createModelMapWithFail(modelMap, e.getMessage(), "buildUpResultWithModification");
        } catch (Exception e){
            return createModelMapWithFail(modelMap, "서버 오류 발생하였습니다.", "buildUpResultWithModification");
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
        modelMap.put("countOfDayOnDayClosingPriceIncrease", buildUp.getCountOfDayOnDayClosingPriceIncrease());
        modelMap.put("countOfDayOnDayClosingPriceDecrease", buildUp.getCountOfDayOnDayClosingPriceDecrease());
        modelMap.put("isError", "false");
    }


    private String createModelMapWithBuilUpException(ModelMap modelMap, String message) {
        modelMap.put("isError", "true");
        modelMap.put("errorMessage", message);
        return "buildUpResult";
    }

    private String createModelMapWithFail(ModelMap modelMap, String message, String viewFileName) {
        modelMap.put("isError", "true");
        modelMap.put("errorMessage", message);
        return viewFileName;
    }
}
