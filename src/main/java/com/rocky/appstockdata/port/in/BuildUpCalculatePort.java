package com.rocky.appstockdata.port.in;

import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.BuildUpSourceDTO;

public interface BuildUpCalculatePort {

    BuildUp calculateBuildUp(BuildUpSourceDTO buildUpSourceDTO);
}
