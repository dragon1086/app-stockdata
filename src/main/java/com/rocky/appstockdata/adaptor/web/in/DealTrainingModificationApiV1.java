package com.rocky.appstockdata.adaptor.web.in;

import com.rocky.appstockdata.application.port.in.DealTrainingUseCase;
import com.rocky.appstockdata.application.port.in.SupabaseUseCase;
import com.rocky.appstockdata.domain.DailyDealHistory;
import com.rocky.appstockdata.domain.DealModification;
import com.rocky.appstockdata.domain.DealTrainingResult;
import com.rocky.appstockdata.domain.dto.*;
import com.rocky.appstockdata.domain.validator.DealTrainingSourceValidator;
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
public class DealTrainingModificationApiV1 {
    private final DealTrainingUseCase dealTrainingUseCase;
    private final SupabaseUseCase supabaseUseCase;

    public DealTrainingModificationApiV1(DealTrainingUseCase dealTrainingUseCase,
                                         SupabaseUseCase supabaseUseCase) {
        this.dealTrainingUseCase = dealTrainingUseCase;
        this.supabaseUseCase = supabaseUseCase;
    }

    @PostMapping("/deal-calculate-modify")
    @ResponseBody
    public void dealCalculateModify(HttpServletResponse response,
                                    HttpSession session,
                                    @RequestBody DealTrainingModificationSourceDTO request) throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        try{
            UserDTO user = (UserDTO) session.getAttribute("sessionUser");

            DealTrainingSourceDTO dealTrainingSourceDTO = DealTrainingSourceDTO.builder()
                    .companyName(request.getCompanyName())
                    .startDate(request.getStartDate())
                    .endDate(request.getEndDate())
                    .slotAmount(request.getSlotAmount())
                    .portion(request.getPortion())
                    .initialPortion(request.getInitialPortion())
                    .dealModifications(createDealModifications(
                            request.getModifyDates(),
                            request.getSellPercents(),
                            request.getSellPrices(),
                            request.getBuyPercents(),
                            request.getBuyPrices()))
                    .jumpDate(request.getJumpDate())
                    .userId(user != null ? user.getId() : null)
                    .id(request.getHistoryId())
                    .build();

            DealTrainingSourceValidator.validate(dealTrainingSourceDTO);

            DealTrainingResult dealTrainingResult = dealTrainingUseCase.modifyDailyDeal(dealTrainingSourceDTO);

            Long savedHistoryId = null;
            if(user != null) {
                savedHistoryId = supabaseUseCase.upsertDealTrainingSource(user, dealTrainingSourceDTO);
            }

            List<DailyDealHistory> dailyDealHistories = dealTrainingResult.getDailyDealHistories();
            int deltaCountByJump = dealTrainingResult.getDeltaCountByJump();

            response.getWriter().print(JSONObject.fromObject(
                    DealTrainingResponseDTO.builder()
                    .historyId(savedHistoryId)
                    .companyName(dealTrainingSourceDTO.getCompanyName())
                    .startDate(dealTrainingResult.getStartDate())
                    .endDate(dealTrainingResult.getEndDate())
                    .itemName(dealTrainingResult.getItemName())
                    .lastDailyDealHistory(JSONObject.fromObject(dailyDealHistories.get(dailyDealHistories.size() - 1)))
                    .oneDayAgoDailyDealHistory(JSONObject.fromObject(dailyDealHistories.get(dailyDealHistories.size() - 2)))
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
                    .deltaDailyDealHistories(deltaCountByJump != 0 ? JSONArray.fromObject(dailyDealHistories.subList(dailyDealHistories.size() - deltaCountByJump - 1 , dailyDealHistories.size())) : new JSONArray())
                    .isError(false)
                    .build()));

        } catch (NumberFormatException e){
            response.getWriter().print(JSONObject.fromObject(
                    DealTrainingResponseDTO.builder()
                    .isError(true)
                    .errorMessage("올바른 데이터 입력 형식이 아닙니다. 뒤로 돌아가서 정확한 형식으로 넣어주세요.")
                    .build()));
        } catch (NoResultDataException e){
            response.getWriter().print(JSONObject.fromObject(
                    DealTrainingResponseDTO.builder()
                            .isError(true)
                            .errorMessage("다음에 진행할 일봉차트가 없습니다.")
                            .build()));
        } catch (Exception e){
            log.error("서버 오류 발생하였습니다. : {}", e.getMessage());
            response.getWriter().print(JSONObject.fromObject(
                    DealTrainingResponseDTO.builder()
                    .isError(true)
                    .errorMessage("서버 오류 발생하였습니다.")
                    .build()));
        }
    }

    private List<DealModification> createDealModifications(List<String> modifyDates, List<String> sellPercents, List<String> sellPrices, List<String> buyPercents, List<String> buyPrices) {
        List<DealModification> dealModifications = new ArrayList<>();
        for(int i = 0; i < modifyDates.size(); i++){
            if(StringUtils.isEmpty(modifyDates.get(i))){
                continue;
            }

            dealModifications.add(DealModification.builder()
                    .modifyDate(modifyDates.get(i))
                    .buyPercent(buyPercents.get(i))
                    .buyPrice(buyPrices.get(i))
                    .sellPercent(sellPercents.get(i))
                    .sellPrice(sellPrices.get(i))
                    .build());
        }
        return dealModifications;
    }

    @PostMapping(path = "/deal-calculate-copy")
    @ResponseBody
    public void dealCalculateCopy(HttpServletResponse response,
                                    @RequestBody DealTrainingModificationSourceDTO request) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        try{
            DealTrainingSourceDTO dealTrainingSourceDTO = DealTrainingSourceDTO.builder()
                    .companyName(request.getCompanyName())
                    .startDate(request.getStartDate())
                    .endDate(request.getEndDate())
                    .slotAmount(request.getSlotAmount())
                    .portion(request.getPortion())
                    .dealModifications(createDealModifications(
                            request.getModifyDates(),
                            request.getSellPercents(),
                            request.getSellPrices(),
                            request.getBuyPercents(),
                            request.getBuyPrices()))
                    .build();

            DealTrainingSourceValidator.validate(dealTrainingSourceDTO);

            DealTrainingResult dealTrainingResult = dealTrainingUseCase.modifyDailyDeal(dealTrainingSourceDTO);

            response.getWriter().print(JSONObject.fromObject(
                    DealTrainingResponseDTO.builder()
                            .redirectUrl("/deal-training-copy")
                            .companyName(dealTrainingSourceDTO.getCompanyName())
                            .startDate(dealTrainingResult.getStartDate())
                            .endDate(dealTrainingResult.getEndDate())
                            .itemName(dealTrainingResult.getItemName())
                            .lastDailyDealHistory(JSONObject.fromObject(dealTrainingResult.getDailyDealHistories().get(dealTrainingResult.getDailyDealHistories().size() - 1)))
                            .oneDayAgoDailyDealHistory(JSONObject.fromObject(dealTrainingResult.getDailyDealHistories().get(dealTrainingResult.getDailyDealHistories().size() - 2)))
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
                            .dailyDealHistories(JSONArray.fromObject(dealTrainingResult.getDailyDealHistories()))
                            .isError(false)
                            .build()));
        } catch (NumberFormatException e){
            response.getWriter().print(JSONObject.fromObject(
                    DealTrainingResponseDTO.builder()
                            .isError(true)
                            .errorMessage("올바른 데이터 입력 형식이 아닙니다. 뒤로 돌아가서 정확한 형식으로 넣어주세요.")
                            .build()));
        } catch (NoResultDataException e){
            response.getWriter().print(JSONObject.fromObject(
                    DealTrainingResponseDTO.builder()
                            .isError(true)
                            .errorMessage("다음에 진행할 일봉차트가 없습니다.")
                            .build()));
        } catch (Exception e){
            log.error("서버 오류 발생하였습니다. : {}", e.getMessage());
            response.getWriter().print(JSONObject.fromObject(
                    DealTrainingResponseDTO.builder()
                            .isError(true)
                            .errorMessage("서버 오류 발생하였습니다.")
                            .build()));
        }
    }

    @GetMapping("/deal-calculate-histories")
    public String getDealTrainingHistories(HttpSession session, ModelMap modelMap){
        try{
            UserDTO user = (UserDTO) session.getAttribute("sessionUser");
            if(user == null) {
                log.error("로그인 된 유저 정보가 없습니다");
                DealTrainingHistoryDTO result = DealTrainingHistoryDTO.builder()
                        .isError(true)
                        .errorMessage("로그인 된 유저 정보가 없습니다")
                        .redirectUrl("/")
                        .build();
                modelMap.put("isError", false);
                modelMap.put("errorMessage", "로그인 된 유저 정보가 없습니다");
                modelMap.put("redirectUrl", "/");
                return "dealTrainingHistory";
            }

            List<DealTrainingSourceDTO> dealTrainingSources = supabaseUseCase.getDealTrainingHistories(user.getId(), user.getAccessToken());
            DealTrainingHistoryDTO result = DealTrainingHistoryDTO.builder()
                    .isError(false)
                    .dealTrainingSourceDTOs(dealTrainingSources)
                    .build();

            modelMap.put("dealTrainingHistories", result);
            modelMap.put("isError", false);
            modelMap.put("dealTrainingSourceDTOs", JSONArray.fromObject(dealTrainingSources));

            return "dealTrainingHistory";
        } catch (Exception e){
            log.error("서버 오류 발생하였습니다. : {}", e.getMessage());
            DealTrainingHistoryDTO result = DealTrainingHistoryDTO.builder()
                    .isError(true)
                    .errorMessage("서버 오류 발생하였습니다.")
                    .redirectUrl("/")
                    .build();
            modelMap.put("isError", false);
            modelMap.put("errorMessage", "서버 오류 발생하였습니다.");
            modelMap.put("redirectUrl", "/");
            return "dealTrainingHistory";
        }
    }

    @PostMapping(path = "/previous-deal-calculate",  produces = MediaType.APPLICATION_JSON_VALUE)
    public String loadPreviousDealTraining(HttpSession session,
                                         ModelMap modelMap,
                                         @ModelAttribute DealTrainingSourceDTO request) {

        try{
            UserDTO user = (UserDTO) session.getAttribute("sessionUser");
            if(user == null) {
                log.error("로그인 된 유저 정보가 없습니다");
                modelMap.put("isError", "true");
                modelMap.put("errorMessage", "로그인 된 유저 정보가 없습니다");
                return "dealTraining";
            }

            DealTrainingSourceValidator.validate(request);

            DealTrainingResult dealTrainingResult = dealTrainingUseCase.modifyDailyDeal(request);

            setModelMap(modelMap, dealTrainingResult, request, request.getId());

        } catch (Exception e){
            log.error("서버 오류 발생하였습니다. : {}", e.getMessage());
            modelMap.put("isError", "true");
            modelMap.put("errorMessage", e.getMessage());
            return "dealTraining";
        }

        return "dealTraining";
    }

    private void setModelMap(ModelMap modelMap, DealTrainingResult dealTrainingResult, DealTrainingSourceDTO dealTrainingSourceDTO, Long historyId) {
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
        modelMap.put("historyId", historyId);
    }
}
