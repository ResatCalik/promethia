package com.semiarc.promethia.user.controller;

import com.semiarc.promethia.common.response.ApiResponse;
import com.semiarc.promethia.user.dto.UserProfileResponse;
import com.semiarc.promethia.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        UserProfileResponse response = userService.getCurrentUserProfile(userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.<UserProfileResponse>builder()
                        .success(true)
                        .message("Current user fetched successfully")
                        .data(response)
                        .build()
        );
    }
}