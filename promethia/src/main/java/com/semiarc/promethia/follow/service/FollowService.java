package com.semiarc.promethia.follow.service;

import com.semiarc.promethia.follow.dto.FollowListItemResponse;
import com.semiarc.promethia.follow.dto.FollowUserResponse;

import java.util.List;

public interface FollowService {

    FollowUserResponse followUser(String currentUserEmail, Long targetUserId);

    FollowUserResponse unfollowUser(String currentUserEmail, Long targetUserId);

    List<FollowListItemResponse> getFollowers(Long userId);

    List<FollowListItemResponse> getFollowing(Long userId);
}