package com.semiarc.promethia.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostImageResponse {

    private Long id;
    private String imageUrl;
    private Integer displayOrder;
}