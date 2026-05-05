package com.semiarc.promethia.like.service;

import com.semiarc.promethia.common.exception.BusinessException;
import com.semiarc.promethia.common.exception.ResourceNotFoundException;
import com.semiarc.promethia.like.domain.PostLike;
import com.semiarc.promethia.like.dto.PostLikeResponse;
import com.semiarc.promethia.like.repository.PostLikeRepository;
import com.semiarc.promethia.post.domain.Post;
import com.semiarc.promethia.post.repository.PostRepository;
import com.semiarc.promethia.user.domain.User;
import com.semiarc.promethia.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public PostLikeResponse likePost(String userEmail, Long postId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (postLikeRepository.existsByPostAndUser(post, user)) {
            throw new BusinessException("You already liked this post");
        }

        PostLike postLike = PostLike.builder()
                .post(post)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        postLikeRepository.save(postLike);

        long likeCount = postLikeRepository.countByPost(post);

        return PostLikeResponse.builder()
                .postId(post.getId())
                .userId(user.getId())
                .likeCount(likeCount)
                .message("Post liked successfully")
                .build();
    }

    @Override
    @Transactional
    public PostLikeResponse unlikePost(String userEmail, Long postId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        PostLike postLike = postLikeRepository.findByPostAndUser(post, user)
                .orElseThrow(() -> new ResourceNotFoundException("Like not found"));

        postLikeRepository.delete(postLike);

        long likeCount = postLikeRepository.countByPost(post);

        return PostLikeResponse.builder()
                .postId(post.getId())
                .userId(user.getId())
                .likeCount(likeCount)
                .message("Post unliked successfully")
                .build();
    }
}