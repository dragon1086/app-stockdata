package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.application.port.in.BuildUpCalculateUseCase;
import com.rocky.appstockdata.application.port.in.CompanyNameSearchUseCase;
import com.rocky.appstockdata.config.BuildUpServiceFactory;
import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.dto.BuildUpModificationSourceDTO;
import com.rocky.appstockdata.domain.dto.BuildUpSourceDTO;
import com.rocky.appstockdata.exceptions.BuildUpSourceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuildUpCalculateService implements BuildUpCalculateUseCase {
    private final BuildUpServiceFactory buildUpServiceFactory;
    private final CompanyNameSearchUseCase companyNameSearchUseCase;

    public BuildUpCalculateService(BuildUpServiceFactory buildUpServiceFactory, CompanyNameSearchUseCase companyNameSearchUseCase) {
        this.buildUpServiceFactory = buildUpServiceFactory;
        this.companyNameSearchUseCase = companyNameSearchUseCase;
    }


    @Override
    public BuildUp calculateBuildUp(BuildUpSourceDTO buildUpSourceDTO) throws BuildUpSourceException {
        BuildUpSourceDTO requestData = buildUpSourceDTO;
        if(StringUtils.isEmpty(buildUpSourceDTO.getCompanyName())){
            requestData = buildUpSourceDTO.createRandomCompanyName(companyNameSearchUseCase.getRandomCompanyName());
        }

        return buildUpServiceFactory.getService(BuildUpType.get(requestData.getSimulationMode())
                                                        .orElseThrow(() -> new BuildUpSourceException("선택하신 시뮬레이션 모드가 존재하지 않습니다.")))
                .calculateBuildUp(requestData);
    }

    @Override
    public BuildUp calculateBuildUpModification(BuildUpModificationSourceDTO buildUpModificationSourceDTO) throws BuildUpSourceException {
        return buildUpServiceFactory.getService(BuildUpType.get(buildUpModificationSourceDTO.getSimulationMode())
                                                        .orElseThrow(() -> new BuildUpSourceException("선택하신 시뮬레이션 모드가 존재하지 않습니다.")))
                .calculateBuildUpModification(buildUpModificationSourceDTO);
    }
}
