package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.application.port.in.BuildUpCalculateUseCase;
import com.rocky.appstockdata.config.BuildUpServiceFactory;
import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.BuildUpModificationSourceDTO;
import com.rocky.appstockdata.domain.BuildUpSourceDTO;
import com.rocky.appstockdata.exceptions.BuildUpSourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuildUpCalculateService implements BuildUpCalculateUseCase {
    final BuildUpServiceFactory buildUpServiceFactory;

    public BuildUpCalculateService(BuildUpServiceFactory buildUpServiceFactory) {
        this.buildUpServiceFactory = buildUpServiceFactory;
    }


    @Override
    public BuildUp calculateBuildUp(BuildUpSourceDTO buildUpSourceDTO) throws BuildUpSourceException {
        return buildUpServiceFactory.getService(BuildUpType.get(buildUpSourceDTO.getSimulationMode())
                                                        .orElseThrow(() -> new BuildUpSourceException("선택하신 시뮬레이션 모드가 존재하지 않습니다.")))
                .calculateBuildUp(buildUpSourceDTO);
    }

    @Override
    public BuildUp calculateBuildUpModification(BuildUpModificationSourceDTO buildUpModificationSourceDTO) throws BuildUpSourceException {
        return buildUpServiceFactory.getService(BuildUpType.get(buildUpModificationSourceDTO.getSimulationMode())
                                                        .orElseThrow(() -> new BuildUpSourceException("선택하신 시뮬레이션 모드가 존재하지 않습니다.")))
                .calculateBuildUpModification(buildUpModificationSourceDTO);
    }
}
