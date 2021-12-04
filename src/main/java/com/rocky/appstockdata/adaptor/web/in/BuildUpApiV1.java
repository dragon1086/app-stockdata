package com.rocky.appstockdata.adaptor.web.in;

import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.BuildUpSourceDTO;
import com.rocky.appstockdata.domain.validator.BuildUpSourceValidator;
import com.rocky.appstockdata.exceptions.BuildUpSourceException;
import com.rocky.appstockdata.exceptions.NoResultDataException;
import com.rocky.appstockdata.application.port.in.BuildUpCalculateUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BuildUpApiV1 {
    private final BuildUpCalculateUseCase buildUpCalculateService;

    public BuildUpApiV1(BuildUpCalculateUseCase buildUpCalculateService) {
        this.buildUpCalculateService = buildUpCalculateService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping(value = "buildup-calculate")
    public String buildUpResultController(ModelMap modelMap,
                                          @RequestParam(value = "companyName", required = false) String companyName,
                                          @RequestParam(value = "buildupAmount", required = false) String buildupAmount,
                                          @RequestParam(value = "startDate", required = false) String startDate,
                                          @RequestParam(value = "endDate", required = false) String endDate){

        try{
            BuildUpSourceDTO buildUpSourceDTO = BuildUpSourceDTO.builder()
                    .companyName(companyName)
                    .buildupAmount(Long.parseLong(buildupAmount))
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();
            BuildUpSourceValidator.validate(buildUpSourceDTO);

            BuildUp buildUp = buildUpCalculateService.calculateBuildUp(buildUpSourceDTO);

            setModelMap(modelMap, buildUp);
        }catch (BuildUpSourceException | NoResultDataException e){
            return createModelMapWithBuilUpException(modelMap, e.getMessage());
        }catch (NumberFormatException e){
            return createModelMapWithNumberFormatException(modelMap, "빌드업 금액 입력 시 숫자로 입력하셔야 합니다.");
        }

        return "buildUpResult";
    }

    private void setModelMap(ModelMap modelMap, BuildUp buildUp) {
        modelMap.put("earningRate", buildUp.getEarningRate());
        modelMap.put("earningAmount", buildUp.getEarningAmount());
        modelMap.put("totalAmount", buildUp.getTotalAmount());
        modelMap.put("sumOfPurchaseAmount", buildUp.getSumOfPurchaseAmount());
        modelMap.put("dailyDealHistories", buildUp.getDailyDealHistories());
        modelMap.put("isError", "false");
    }

    private String createModelMapWithBuilUpException(ModelMap modelMap, String message) {
        modelMap.put("isError", "true");
        modelMap.put("errorMessage", message);
        return "buildUpResult";
    }

    private String createModelMapWithNumberFormatException(ModelMap modelMap, String s) {
        modelMap.put("isError", "true");
        modelMap.put("errorMessage", s);
        return "buildUpResult";
    }

}
