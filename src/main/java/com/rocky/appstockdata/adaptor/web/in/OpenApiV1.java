package com.rocky.appstockdata.adaptor.web.in;

import com.rocky.appstockdata.application.port.in.DealDataUseCase;
import com.rocky.appstockdata.domain.validator.OpenApiValidator;
import com.rocky.appstockdata.exceptions.NotAllowedException;
import io.micrometer.core.annotation.Incubating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class OpenApiV1 {
    private final DealDataUseCase dealDataUseCase;

    public OpenApiV1(DealDataUseCase dealDataUseCase) {
        this.dealDataUseCase = dealDataUseCase;
    }

//    @RequestMapping(value = "/1month-to-today/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public String getDealListFrom1MonthsAgoToToday(@PathVariable String id) {
        try {
            OpenApiValidator.idValidator(id);
            return dealDataUseCase.from1MonthsAgoToTodayDeals(id);
        } catch (NotAllowedException e){
            return e.getMessage();
        }

    }

//    @RequestMapping(value = "/2month-to-1month/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public String getDealListFrom2MonthsAgoTo1MonthsAgo(@PathVariable String id) {
        try {
            OpenApiValidator.idValidator(id);
            return dealDataUseCase.from2MonthsAgoTo1MonthsAgoDeals(id);
        } catch (NotAllowedException e){
            return e.getMessage();
        }

    }

//    @RequestMapping(value = "/3month-to-today/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public String getDealListFrom3MonthsAgoToToday(@PathVariable String id) {
        try {
            OpenApiValidator.idValidator(id);
            return dealDataUseCase.from3MonthsAgoToTodayDeals(id);
        } catch (NotAllowedException e){
            return e.getMessage();
        }

    }
}
