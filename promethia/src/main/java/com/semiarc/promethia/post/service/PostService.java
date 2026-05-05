package com.semiarc.promethia.post.service;

import com.semiarc.promethia.post.dto.CreatePostRequest;
import com.semiarc.promethia.post.dto.PostDetailResponse;
import com.semiarc.promethia.post.dto.PostSummaryResponse;

import java.util.List;

public interface PostService {

    PostDetailResponse createPost(String userEmail, CreatePostRequest request);

    PostDetailResponse getPostById(Long postId);

    List<PostSummaryResponse> getMyPosts(String userEmail);

    List<PostSummaryResponse> getPostsByUserId(Long userId);

    List<PostSummaryResponse> getFeed(String userEmail);
}