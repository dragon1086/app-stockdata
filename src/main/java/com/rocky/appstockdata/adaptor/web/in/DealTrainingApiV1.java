package com.rocky.appstockdata.adaptor.web.in;

import com.rocky.appstockdata.application.port.in.DealTrainingUseCase;
import com.rocky.appstockdata.application.port.in.SupabaseUseCase;
import com.rocky.appstockdata.domain.DealModification;
import com.rocky.appstockdata.domain.DealTrainingResult;
import com.rocky.appstockdata.domain.dto.DealTrainingModificationSourceDTO;
import com.rocky.appstockdata.domain.dto.DealTrainingResponseDTO;
import com.rocky.appstockdata.domain.dto.DealTrainingSourceDTO;
import com.rocky.appstockdata.domain.dto.UserDTO;
import com.rocky.appstockdata.domain.validator.DealTrainingSourceValidator;
import com.rocky.appstockdata.exceptions.DealTrainingSourceException;
import com.rocky.appstockdata.exceptions.NoResultDataException;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class DealTrainingApiV1 {
    private final DealTrainingUseCase dealTrainingUseCase;
    private final SupabaseUseCase supabaseUseCase;

    public DealTrainingApiV1(DealTrainingUseCase dealTrainingUseCase,
                             SupabaseUseCase supabaseUseCase) {
        this.dealTrainingUseCase = dealTrainingUseCase;
        this.supabaseUseCase = supabaseUseCase;
    }

    @GetMapping("/new-deal-training")
    public String serveReactApp() {
        return "forward:/react-app/index.html";
    }

    @GetMapping("/")
    public String dealTrainingMain(ModelMap modelMap,
                                   @RequestParam(required = false) String error){
        if (error != null && !error.isEmpty()) {
            String errorMessage;
            switch (error) {
                case "Verification failed":
                    errorMessage = "인증에 실패했습니다. 다시 시도해 주세요.";
                    break;
                case "No session":
                    errorMessage = "세션이 만료되었습니다. 다시 로그인해 주세요.";
                    break;
                default:
                    errorMessage = "오류가 발생했습니다: " + error;
            }
            modelMap.put("isError", "true");
            modelMap.put("errorMessage", errorMessage);
        }
        return "dealTrainingIndex";
    }

    @PostMapping(path = "/deal-calculate", produces = MediaType.APPLICATION_JSON_VALUE)
    public String dealCalculate(HttpSession session,
                                ModelMap modelMap,
                                @RequestParam(value = "companyName", required = false) String companyName,
                                @RequestParam(value = "slotAmount", required = false) String slotAmount,
                                @RequestParam(value = "portion", required = false) String portion,
                                @RequestParam(value = "startDate", required = false) String startDate,
                                @RequestParam(value = "valuationPercent", required = false) String valuationPercent,
                                @RequestParam(value = "level", required = false) String level){
        UserDTO user = (UserDTO) session.getAttribute("sessionUser");

        try{
            if(user != null) {
                UserDTO updatedUser = supabaseUseCase.verifyAndGetUser(user.getAccessToken());
                session.setAttribute("sessionUser", updatedUser);
            }

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
            log.error(e.getMessage(), e);
            return createModelMapWithDealTrainingSourceException(modelMap, "서버 오류 발생");
        }

        return "dealTraining";
    }

    private void setModelMap(ModelMap modelMap, DealTrainingResult dealTrainingResult, DealTrainingSourceDTO dealTrainingSourceDTO) {
        modelMap.put("companyName", dealTrainingSourceDTO.getCompanyName());
        modelMap.put("startDate", dealTrainingResult.getStartDate());
        modelMap.put("endDate", dealTrainingResult.getEndDate());
        modelMap.put("itemName", dealTrainingResult.getItemName());
        modelMap.put("dailyDealHistories", JSONArray.fromObject(dealTrainingResult.getDailyDealHistories()));
        modelMap.put("initialPortion", dealTrainingSourceDTO.getPortion());
        modelMap.put("slotAmount", dealTrainingSourceDTO.getSlotAmount());
        modelMap.put("portion", 100.0 - dealTrainingResult.getRemainingPortion());
        modelMap.put("remainingSlotAmount", dealTrainingResult.getRemainingSlotAmount());
        modelMap.put("remainingPortion", dealTrainingResult.getRemainingPortion());
        modelMap.put("dealModifications", JSONArray.fromObject(dealTrainingResult.getDealModifications()));
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

    @GetMapping("/dealTrainingManual")
    public String dealTrainingManual(){
        return "dealTrainingManual";
    }

    @GetMapping("/deal-training-copy")
    public String dealTrainingCopy(){
        return "dealTrainingCopy";
    }
}