package com.rocky.appstockdata.application.port.in;

import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.BuildUpModificationSourceDTO;
import com.rocky.appstockdata.domain.BuildUpSourceDTO;

public interface MinusCandleBuildUpUseCase {

    BuildUp calculateBuildUp(BuildUpSourceDTO buildUpSourceDTO);

    BuildUp calculateBuildUpModification(BuildUpModificationSourceDTO buildUpModificationSourceDTO);
}
