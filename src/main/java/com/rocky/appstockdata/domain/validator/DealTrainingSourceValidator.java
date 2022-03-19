package com.rocky.appstockdata.domain.validator;

import com.rocky.appstockdata.domain.dto.DealTrainingSourceDTO;
import com.rocky.appstockdata.exceptions.DealTrainingSourceException;
import org.apache.commons.lang3.StringUtils;

public class DealTrainingSourceValidator {
    public static void validate(DealTrainingSourceDTO dealTrainingSourceDTO) throws DealTrainingSourceException {
        if(dealTrainingSourceDTO.getSlotAmount() == null){
            throw new DealTrainingSourceException("배분할 금액을 입력하셔야 합니다");
        }
        if(dealTrainingSourceDTO.getPortion() == null){
            throw new DealTrainingSourceException("시작 비중을 입력하셔야 합니다");
        }
    }
}
