package com.rocky.appstockdata.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String email;
    private String fullName;
    private String accessToken;
}
