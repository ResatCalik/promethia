package com.semiarc.promethia.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostDetailResponse {

    private Long id;
    private String title;
    private String summary;
    private String detailContent;
    private Long authorId;
    private String authorUsername;
    private List<PostImageResponse> images;
    private long likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}