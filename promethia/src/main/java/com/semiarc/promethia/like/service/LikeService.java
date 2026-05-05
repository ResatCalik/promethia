package com.semiarc.promethia.like.service;

import com.semiarc.promethia.like.dto.PostLikeResponse;

public interface LikeService {

    PostLikeResponse likePost(String userEmail, Long postId);

    PostLikeResponse unlikePost(String userEmail, Long postId);
}