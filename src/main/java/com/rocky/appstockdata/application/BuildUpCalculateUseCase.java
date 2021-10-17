package com.rocky.appstockdata.application;

import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.BuildUpSourceDTO;
import com.rocky.appstockdata.port.in.BuildUpCalculatePort;
import org.springframework.stereotype.Service;

@Service
public class BuildUpCalculateUseCase implements BuildUpCalculatePort {
    @Override
    public BuildUp calculateBuildUp(BuildUpSourceDTO buildUpSourceDTO) {
        return BuildUp.builder()
                .earningRate(21.5f)
                .earningAmount(15000L)
                .totalAmount(45000L)
                .build();
    }
}
