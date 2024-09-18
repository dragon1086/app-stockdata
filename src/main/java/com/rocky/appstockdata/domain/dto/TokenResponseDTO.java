package com.rocky.appstockdata.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TokenResponseDTO {
    private String accessToken;
    private String tokenType;
    private int expiresIn;
    private String refreshToken;
}
