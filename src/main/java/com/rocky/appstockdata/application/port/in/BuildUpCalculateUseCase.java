package com.rocky.appstockdata.application.port.in;

import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.BuildUpSourceDTO;

public interface BuildUpCalculateUseCase {

    BuildUp calculateBuildUp(BuildUpSourceDTO buildUpSourceDTO);
}
