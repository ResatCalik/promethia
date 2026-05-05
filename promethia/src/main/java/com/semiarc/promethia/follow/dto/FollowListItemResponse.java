package com.semiarc.promethia.follow.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowListItemResponse {

    private Long userId;
    private String username;
    private String email;
    private String bio;
    private String avatarUrl;
}