package com.rocky.appstockdata.adaptor.web.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocky.appstockdata.application.port.in.CompanyNameSearchUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class CompanyApiV1 {
    private final CompanyNameSearchUseCase companyNameSearchUseCase;

    public CompanyApiV1(CompanyNameSearchUseCase companyNameSearchUseCase) {
        this.companyNameSearchUseCase = companyNameSearchUseCase;
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
