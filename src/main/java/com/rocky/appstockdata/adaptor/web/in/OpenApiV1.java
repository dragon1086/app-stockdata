package com.rocky.appstockdata.adaptor.web.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocky.appstockdata.application.port.in.BuildUpCalculateUseCase;
import com.rocky.appstockdata.application.port.in.CompanyNameSearchUseCase;
import com.rocky.appstockdata.application.port.in.DealDataUseCase;
import com.rocky.appstockdata.application.port.in.DealTrainingUseCase;
import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.DealModification;
import com.rocky.appstockdata.domain.DealTrainingResult;
import com.rocky.appstockdata.domain.dto.BuildUpModificationSourceDTO;
import com.rocky.appstockdata.domain.dto.BuildUpSourceDTO;
import com.rocky.appstockdata.domain.dto.DailyDealSmallDTO;
import com.rocky.appstockdata.domain.dto.DealTrainingSourceDTO;
import com.rocky.appstockdata.domain.validator.BuildUpSourceValidator;
import com.rocky.appstockdata.domain.validator.DealTrainingSourceValidator;
import com.rocky.appstockdata.domain.validator.OpenApiValidator;
import com.rocky.appstockdata.exceptions.BuildUpSourceException;
import com.rocky.appstockdata.exceptions.DealTrainingSourceException;
import com.rocky.appstockdata.exceptions.NoResultDataException;
import com.rocky.appstockdata.exceptions.NotAllowedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class OpenApiV1 {
    private final DealDataUseCase dealDataUseCase;

    public OpenApiV1(DealDataUseCase dealDataUseCase) {
        this.dealDataUseCase = dealDataUseCase;
    }

    @RequestMapping(value = "/3month-to-today/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public String getDealListFrom3MonthsAgoToToday(@PathVariable String id) {
        try {
            OpenApiValidator.idValidator(id);
            List<DailyDealSmallDTO> results = dealDataUseCase.from3MonthsAgoToTodayDeals();

            ObjectMapper mapper = new ObjectMapper();

            return mapper.writeValueAsString(results);
        } catch (JsonProcessingException e) {
            return "{\"errorMessage\" :  \"검색 결과 없음\"}";
        } catch (NotAllowedException e){
            return e.getMessage();
        }

    }

    @RequestMapping(value = "/6month-to-3month-ago/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public String getDealListFrom6MonthsAgoTo3MonthsAgo(@PathVariable String id) {
        try {
            OpenApiValidator.idValidator(id);
            List<DailyDealSmallDTO> results = dealDataUseCase.from6MonthsAgoTo3MonthsAgoDeals();

            ObjectMapper mapper = new ObjectMapper();

            return mapper.writeValueAsString(results);
        } catch (JsonProcessingException e) {
            return "{\"errorMessage\" :  \"검색 결과 없음\"}";
        } catch (NotAllowedException e){
            return e.getMessage();
        }

    }

    @RequestMapping(value = "/9month-to-6month-ago/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public String getDealListFrom9MonthsAgoTo6MonthsAgo(@PathVariable String id) {
        try {
            OpenApiValidator.idValidator(id);
            List<DailyDealSmallDTO> results = dealDataUseCase.from9MonthsAgoTo6MonthsAgoDeals();

            ObjectMapper mapper = new ObjectMapper();

            return mapper.writeValueAsString(results);
        } catch (JsonProcessingException e) {
            return "{\"errorMessage\" :  \"검색 결과 없음\"}";
        } catch (NotAllowedException e){
            return e.getMessage();
        }

    }

    @RequestMapping(value = "/12month-to-9month-ago/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public String getDealListFrom12MonthsAgoTo9MonthsAgo(@PathVariable String id) {
        try {
            OpenApiValidator.idValidator(id);
            List<DailyDealSmallDTO> results = dealDataUseCase.from12MonthsAgoTo9MonthsAgoDeals();

            ObjectMapper mapper = new ObjectMapper();

            return mapper.writeValueAsString(results);
        } catch (JsonProcessingException e) {
            return "{\"errorMessage\" :  \"검색 결과 없음\"}";
        } catch (NotAllowedException e){
            return e.getMessage();
        }
    }
}
