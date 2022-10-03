package com.rocky.appstockdata.adaptor.web.in;

import com.rocky.appstockdata.application.port.in.DealTrainingUseCase;
import com.rocky.appstockdata.domain.DealModification;
import com.rocky.appstockdata.domain.DealTrainingResult;
import com.rocky.appstockdata.domain.dto.DealTrainingResponseDTO;
import com.rocky.appstockdata.domain.dto.DealTrainingSourceDTO;
import com.rocky.appstockdata.domain.validator.DealTrainingSourceValidator;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class DealTrainingModificationApiV1 {
    private final DealTrainingUseCase dealTrainingUseCase;

    public DealTrainingModificationApiV1(DealTrainingUseCase dealTrainingUseCase) {
        this.dealTrainingUseCase = dealTrainingUseCase;
    }

    @PostMapping("/deal-calculate-modify")
    @ResponseBody
    public void dealCalculateModify(HttpServletResponse response,
                                    @RequestParam(value = "companyName") String companyName,
                                    @RequestParam("startDate") String startDate,
                                    @RequestParam("endDate") String endDate,
                                    @RequestParam(value = "slotAmount") String slotAmount,
                                    @RequestParam(value = "portion") String portion,
                                    @RequestParam("modifyDate") String[] modifyDates,
                                    @RequestParam(value = "sellPercent", defaultValue = "0") String[] sellPercents,
                                    @RequestParam(value = "sellPrice", defaultValue = "0") String[] sellPrices,
                                    @RequestParam(value = "buyPercent", defaultValue = "0") String[] buyPercents,
                                    @RequestParam(value = "buyPrice", defaultValue = "0") String[] buyPrices) throws IOException {

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

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(JSONObject.fromObject(
                    DealTrainingResponseDTO.builder()
                    .companyName(dealTrainingSourceDTO.getCompanyName())
                    .startDate(dealTrainingResult.getStartDate())
                    .endDate(dealTrainingResult.getEndDate())
                    .itemName(dealTrainingResult.getItemName())
                    .lastDailyDealHistory(JSONObject.fromObject(dealTrainingResult.getDailyDealHistories().get(dealTrainingResult.getDailyDealHistories().size() - 1)))
                    .slotAmount(dealTrainingSourceDTO.getSlotAmount())
                    .portion(100.0 - dealTrainingResult.getRemainingPortion())
                    .remainingSlotAmount(dealTrainingResult.getRemainingSlotAmount())
                    .remainingPortion(dealTrainingResult.getRemainingPortion())
                    .dealModifications(JSONArray.fromObject(dealTrainingResult.getDealModifications()))
                    .totalAmount(dealTrainingResult.getTotalAmount())
                    .valuationPercent(dealTrainingResult.getValuationPercent())
                    .averageUnitPrice(dealTrainingResult.getAverageUnitPrice())
                    .currentClosingPrice(dealTrainingResult.getCurrentClosingPrice())
                    .nextTryDate(dealTrainingResult.getNextTryDate())
                    .sumOfPurchaseAmount(dealTrainingResult.getSumOfPurchaseAmount())
                    .sumOfSellingAmount(dealTrainingResult.getSumOfSellingAmount())
                    .sumOfCommission(dealTrainingResult.getSumOfCommission())
                    .sumOfPurchaseQuantity(dealTrainingResult.getSumOfPurchaseQuantity())
                    .sumOfSellingQuantity(dealTrainingResult.getSumOfSellingQuantity())
                    .earningRate(dealTrainingResult.getEarningRate())
                    .earningAmount(dealTrainingResult.getEarningAmount())
                    .isError(false)
                    .build()));

        } catch (NumberFormatException e){
            response.getWriter().print(
                    DealTrainingResponseDTO.builder()
                    .isError(true)
                    .errorMessage("올바른 데이터 입력 형식이 아닙니다. 뒤로 돌아가서 정확한 형식으로 넣어주세요.")
                    .build());
        } catch (Exception e){
            log.error("서버 오류 발생하였습니다. : {}", e.getMessage());
            response.getWriter().print(
                    DealTrainingResponseDTO.builder()
                    .isError(true)
                    .errorMessage("서버 오류 발생하였습니다.")
                    .build());
        }
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
}
