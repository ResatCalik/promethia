package com.semiarc.promethia.post.controller;

import com.semiarc.promethia.common.response.ApiResponse;
import com.semiarc.promethia.post.dto.CreatePostRequest;
import com.semiarc.promethia.post.dto.PostDetailResponse;
import com.semiarc.promethia.post.dto.PostSummaryResponse;
import com.semiarc.promethia.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostDetailResponse>> createPost(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreatePostRequest request
    ) {
        PostDetailResponse response = postService.createPost(userDetails.getUsername(), request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PostDetailResponse>builder()
                        .success(true)
                        .message("Post created successfully")
                        .data(response)
                        .build());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> getPostById(@PathVariable Long postId) {
        PostDetailResponse response = postService.getPostById(postId);

        return ResponseEntity.ok(
                ApiResponse.<PostDetailResponse>builder()
                        .success(true)
                        .message("Post fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<PostSummaryResponse>>> getMyPosts(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<PostSummaryResponse> response = postService.getMyPosts(userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.<List<PostSummaryResponse>>builder()
                        .success(true)
                        .message("My posts fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<PostSummaryResponse>>> getPostsByUserId(@PathVariable Long userId) {
        List<PostSummaryResponse> response = postService.getPostsByUserId(userId);

        return ResponseEntity.ok(
                ApiResponse.<List<PostSummaryResponse>>builder()
                        .success(true)
                        .message("User posts fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/feed")
    public ResponseEntity<ApiResponse<List<PostSummaryResponse>>> getFeed(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<PostSummaryResponse> response = postService.getFeed(userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.<List<PostSummaryResponse>>builder()
                        .success(true)
                        .message("Feed fetched successfully")
                        .data(response)
                        .build()
        );
    }
}