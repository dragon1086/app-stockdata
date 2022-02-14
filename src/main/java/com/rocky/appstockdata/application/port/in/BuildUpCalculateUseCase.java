package com.rocky.appstockdata.application.port.in;

import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.dto.BuildUpModificationSourceDTO;
import com.rocky.appstockdata.domain.dto.BuildUpSourceDTO;
import com.rocky.appstockdata.exceptions.BuildUpSourceException;

public interface BuildUpCalculateUseCase {
    BuildUp calculateBuildUp(BuildUpSourceDTO buildUpSourceDTO) throws BuildUpSourceException;

    BuildUp calculateBuildUpModification(BuildUpModificationSourceDTO buildUpModificationSourceDTO) throws BuildUpSourceException;
}
