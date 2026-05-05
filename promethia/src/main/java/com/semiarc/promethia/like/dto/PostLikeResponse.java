package com.semiarc.promethia.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostLikeResponse {

    private Long postId;
    private Long userId;
    private long likeCount;
    private String message;
}