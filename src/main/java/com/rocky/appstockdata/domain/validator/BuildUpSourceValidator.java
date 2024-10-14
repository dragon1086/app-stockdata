package com.rocky.appstockdata.domain.validator;

import com.rocky.appstockdata.domain.dto.BuildUpSourceDTO;
import com.rocky.appstockdata.exceptions.BuildUpSourceException;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BuildUpSourceValidator {
    public static void validate(BuildUpSourceDTO buildUpSourceDTO) throws BuildUpSourceException {
        if(StringUtils.isEmpty(buildUpSourceDTO.getStartDate())){
            throw new BuildUpSourceException("시작 날짜를 입력하셔야 합니다");
        }
        if(StringUtils.isEmpty(buildUpSourceDTO.getEndDate())){
            throw new BuildUpSourceException("매도 날짜를 입력하셔야 합니다.");
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(buildUpSourceDTO.getStartDate(), dateTimeFormatter);
        LocalDate endDate = LocalDate.parse(buildUpSourceDTO.getEndDate(), dateTimeFormatter);
        if(startDate.isAfter(endDate)){
            throw new BuildUpSourceException("시작날짜가 종료날짜보다 이후입니다.");
        }
        if(endDate.minusDays(365 * 10).isAfter(startDate)){
            throw new BuildUpSourceException("과도한 서버 운영비 지출을 방지 하기 위해 시뮬레이션 기간은 10년을 넘어서는 안됩니다ㅜㅜ.");
        }
    }
}
