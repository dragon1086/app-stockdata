package com.rocky.appstockdata.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TokenVerificationRequest {
    private String accessToken;
}
