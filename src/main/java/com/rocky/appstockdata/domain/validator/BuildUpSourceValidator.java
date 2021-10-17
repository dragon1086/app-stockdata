package com.rocky.appstockdata.domain.validator;

import com.rocky.appstockdata.domain.BuildUpSourceDTO;
import com.rocky.appstockdata.exceptions.BuildUpSourceException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BuildUpSourceValidator {
    public static void validate(BuildUpSourceDTO buildUpSourceDTO) throws BuildUpSourceException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        LocalDate startDate = LocalDate.parse(buildUpSourceDTO.getStartDate(), dateTimeFormatter);
        LocalDate endDate = LocalDate.parse(buildUpSourceDTO.getEndDate(), dateTimeFormatter);

        if(startDate.isAfter(endDate)){
            throw new BuildUpSourceException("시작날짜가 종료날짜보다 이후입니다.");
        }
    }
}
