package com.semiarc.promethia.follow.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowUserResponse {

    private Long followerId;
    private Long followingId;
    private String message;
}