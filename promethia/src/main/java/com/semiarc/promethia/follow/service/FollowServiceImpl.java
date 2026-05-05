package com.semiarc.promethia.follow.service;

import com.semiarc.promethia.common.exception.BusinessException;
import com.semiarc.promethia.common.exception.ResourceNotFoundException;
import com.semiarc.promethia.follow.domain.Follow;
import com.semiarc.promethia.follow.dto.FollowListItemResponse;
import com.semiarc.promethia.follow.dto.FollowUserResponse;
import com.semiarc.promethia.follow.repository.FollowRepository;
import com.semiarc.promethia.user.domain.User;
import com.semiarc.promethia.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public FollowUserResponse followUser(String currentUserEmail, Long targetUserId) {
        User follower = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        User following = userRepository.findById(targetUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Target user not found"));

        if (follower.getId().equals(following.getId())) {
            throw new BusinessException("You cannot follow yourself");
        }

        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new BusinessException("You already follow this user");
        }

        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .createdAt(LocalDateTime.now())
                .build();

        followRepository.save(follow);

        return FollowUserResponse.builder()
                .followerId(follower.getId())
                .followingId(following.getId())
                .message("User followed successfully")
                .build();
    }

    @Override
    @Transactional
    public FollowUserResponse unfollowUser(String currentUserEmail, Long targetUserId) {
        User follower = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        User following = userRepository.findById(targetUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Target user not found"));

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new ResourceNotFoundException("Follow relationship not found"));

        followRepository.delete(follow);

        return FollowUserResponse.builder()
                .followerId(follower.getId())
                .followingId(following.getId())
                .message("User unfollowed successfully")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FollowListItemResponse> getFollowers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return followRepository.findAllByFollowingOrderByCreatedAtDesc(user)
                .stream()
                .map(follow -> mapUser(follow.getFollower()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FollowListItemResponse> getFollowing(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return followRepository.findAllByFollowerOrderByCreatedAtDesc(user)
                .stream()
                .map(follow -> mapUser(follow.getFollowing()))
                .toList();
    }

    private FollowListItemResponse mapUser(User user) {
        return FollowListItemResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .bio(user.getBio())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }
}