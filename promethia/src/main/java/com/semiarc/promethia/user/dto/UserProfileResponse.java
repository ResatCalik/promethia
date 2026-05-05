package com.semiarc.promethia.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {

    private Long id;
    private String username;
    private String email;
    private String bio;
    private String avatarUrl;
}