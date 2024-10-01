package com.rocky.appstockdata.application.port.in;

import com.rocky.appstockdata.domain.dto.DealTrainingSourceDTO;
import com.rocky.appstockdata.domain.dto.UserDTO;

import java.util.List;

public interface SupabaseUseCase {
    String getAuthorizationUrl();
    UserDTO verifyAndGetUser(String accessToken);

    Long upsertDealTrainingSource(UserDTO user, DealTrainingSourceDTO dealTrainingSourceDTO);

    List<DealTrainingSourceDTO> getDealTrainingHistories(String userId, String accessToken);
}
