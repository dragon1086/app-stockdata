package com.rocky.appstockdata.application.port.in;

import com.rocky.appstockdata.domain.dto.UserDTO;

public interface SupabaseUseCase {
    String getAuthorizationUrl();
    UserDTO verifyAndGetUser(String accessToken);
}
