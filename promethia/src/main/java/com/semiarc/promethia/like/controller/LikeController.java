package com.semiarc.promethia.like.controller;

import com.semiarc.promethia.common.response.ApiResponse;
import com.semiarc.promethia.like.dto.PostLikeResponse;
import com.semiarc.promethia.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}/like")
    public ResponseEntity<ApiResponse<PostLikeResponse>> likePost(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId
    ) {
        PostLikeResponse response = likeService.likePost(userDetails.getUsername(), postId);

        return ResponseEntity.ok(
                ApiResponse.<PostLikeResponse>builder()
                        .success(true)
                        .message("Like operation successful")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<ApiResponse<PostLikeResponse>> unlikePost(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId
    ) {
        PostLikeResponse response = likeService.unlikePost(userDetails.getUsername(), postId);

        return ResponseEntity.ok(
                ApiResponse.<PostLikeResponse>builder()
                        .success(true)
                        .message("Unlike operation successful")
                        .data(response)
                        .build()
        );
    }
}