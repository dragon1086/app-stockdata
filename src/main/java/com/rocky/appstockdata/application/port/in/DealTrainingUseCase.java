package com.rocky.appstockdata.application.port.in;

import com.rocky.appstockdata.domain.DealTrainingResult;
import com.rocky.appstockdata.domain.DealTrainingSourceDTO;

public interface DealTrainingUseCase {
    DealTrainingResult initializeDailyDeal(DealTrainingSourceDTO dealTrainingSourceDTO);

    DealTrainingResult modifyDailyDeal(DealTrainingSourceDTO dealTrainingSourceDTO);
}
