package com.semiarc.promethia.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {

    private String accessToken;
    private Long userId;
    private String username;
}
