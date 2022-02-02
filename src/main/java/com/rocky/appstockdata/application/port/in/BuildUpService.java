package com.rocky.appstockdata.application.port.in;

import com.rocky.appstockdata.application.service.BuildUpType;
import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.BuildUpModificationSourceDTO;
import com.rocky.appstockdata.domain.BuildUpSourceDTO;
import com.rocky.appstockdata.exceptions.BuildUpSourceException;

public interface BuildUpService {
    BuildUpType getBuildUpType();

    BuildUp calculateBuildUp(BuildUpSourceDTO buildUpSourceDTO) throws BuildUpSourceException;

    BuildUp calculateBuildUpModification(BuildUpModificationSourceDTO buildUpModificationSourceDTO) throws BuildUpSourceException;
}
