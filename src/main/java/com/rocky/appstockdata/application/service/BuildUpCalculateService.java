package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.application.port.in.BuildUpCalculateUseCase;
import com.rocky.appstockdata.application.port.in.DailyClosingPriceBuildUpUseCase;
import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.BuildUpModificationSourceDTO;
import com.rocky.appstockdata.domain.BuildUpSourceDTO;
import com.rocky.appstockdata.exceptions.BuildUpSourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuildUpCalculateService implements BuildUpCalculateUseCase {
    final DailyClosingPriceBuildUpUseCase dailyClosingPriceBuildUpUseCase;
    final MinusCandleBuildUpService minusCandleBuildUpService;

    public BuildUpCalculateService(DailyClosingPriceBuildUpUseCase dailyClosingPriceBuildUpUseCase,
                                   MinusCandleBuildUpService minusCandleBuildUpService) {
        this.dailyClosingPriceBuildUpUseCase = dailyClosingPriceBuildUpUseCase;
        this.minusCandleBuildUpService = minusCandleBuildUpService;
    }

    @Override
    public BuildUp calculateBuildUp(BuildUpSourceDTO buildUpSourceDTO) throws BuildUpSourceException {
        if("dailyClosingPrice".equals(buildUpSourceDTO.getSimulationMode())){
            return dailyClosingPriceBuildUpUseCase.calculateBuildUp(buildUpSourceDTO);
        }
        if("minusCandle".equals(buildUpSourceDTO.getSimulationMode())){
            return minusCandleBuildUpService.calculateBuildUp(buildUpSourceDTO);
        }
        throw new BuildUpSourceException("선택하신 시뮬레이션 모드가 존재하지 않습니다.");
    }

    @Override
    public BuildUp calculateBuildUpModification(BuildUpModificationSourceDTO buildUpModificationSourceDTO) {
        return dailyClosingPriceBuildUpUseCase.calculateBuildUpModification(buildUpModificationSourceDTO);
    }
}
