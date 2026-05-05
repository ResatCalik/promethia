package com.semiarc.promethia.follow.controller;

import com.semiarc.promethia.common.response.ApiResponse;
import com.semiarc.promethia.follow.dto.FollowListItemResponse;
import com.semiarc.promethia.follow.dto.FollowUserResponse;
import com.semiarc.promethia.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userId}/follow")
    public ResponseEntity<ApiResponse<FollowUserResponse>> followUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long userId
    ) {
        FollowUserResponse response = followService.followUser(userDetails.getUsername(), userId);

        return ResponseEntity.ok(
                ApiResponse.<FollowUserResponse>builder()
                        .success(true)
                        .message("Follow operation successful")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{userId}/follow")
    public ResponseEntity<ApiResponse<FollowUserResponse>> unfollowUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long userId
    ) {
        FollowUserResponse response = followService.unfollowUser(userDetails.getUsername(), userId);

        return ResponseEntity.ok(
                ApiResponse.<FollowUserResponse>builder()
                        .success(true)
                        .message("Unfollow operation successful")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<ApiResponse<List<FollowListItemResponse>>> getFollowers(@PathVariable Long userId) {
        List<FollowListItemResponse> response = followService.getFollowers(userId);

        return ResponseEntity.ok(
                ApiResponse.<List<FollowListItemResponse>>builder()
                        .success(true)
                        .message("Followers fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<ApiResponse<List<FollowListItemResponse>>> getFollowing(@PathVariable Long userId) {
        List<FollowListItemResponse> response = followService.getFollowing(userId);

        return ResponseEntity.ok(
                ApiResponse.<List<FollowListItemResponse>>builder()
                        .success(true)
                        .message("Following fetched successfully")
                        .data(response)
                        .build()
        );
    }
}