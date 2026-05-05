package com.semiarc.promethia.post.service;

import com.semiarc.promethia.common.exception.ResourceNotFoundException;
import com.semiarc.promethia.post.domain.Post;
import com.semiarc.promethia.post.domain.PostImage;
import com.semiarc.promethia.post.dto.CreatePostRequest;
import com.semiarc.promethia.post.dto.PostDetailResponse;
import com.semiarc.promethia.post.dto.PostImageResponse;
import com.semiarc.promethia.post.dto.PostSummaryResponse;
import com.semiarc.promethia.post.repository.PostRepository;
import com.semiarc.promethia.user.domain.User;
import com.semiarc.promethia.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.semiarc.promethia.like.repository.PostLikeRepository;
import com.semiarc.promethia.follow.repository.FollowRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final FollowRepository followRepository;

    @Override
    @Transactional
    public PostDetailResponse createPost(String userEmail, CreatePostRequest request) {
        User author = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = Post.builder()
                .title(request.getTitle())
                .summary(request.getSummary())
                .detailContent(request.getDetailContent())
                .author(author)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            for (int i = 0; i < request.getImageUrls().size(); i++) {
                PostImage image = PostImage.builder()
                        .imageUrl(request.getImageUrls().get(i))
                        .displayOrder(i)
                        .post(post)
                        .build();
                post.getImages().add(image);
            }
        }

        Post savedPost = postRepository.save(post);
        return mapToDetailResponse(savedPost);
    }

    @Override
    @Transactional(readOnly = true)
    public PostDetailResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        return mapToDetailResponse(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostSummaryResponse> getMyPosts(String userEmail) {
        User author = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return postRepository.findAllByAuthorOrderByCreatedAtDesc(author)
                .stream()
                .map(this::mapToSummaryResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostSummaryResponse> getPostsByUserId(Long userId) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return postRepository.findAllByAuthorOrderByCreatedAtDesc(author)
                .stream()
                .map(this::mapToSummaryResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostSummaryResponse> getFeed(String userEmail) {
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<User> authors = followRepository.findAllByFollower(currentUser)
                .stream()
                .map(follow -> follow.getFollowing())
                .toList();

        authors = new java.util.ArrayList<>(authors);
        authors.add(currentUser);

        return postRepository.findAllByAuthorInOrderByCreatedAtDesc(authors)
                .stream()
                .map(this::mapToSummaryResponse)
                .toList();
    }

    private PostSummaryResponse mapToSummaryResponse(Post post) {
        return PostSummaryResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .summary(post.getSummary())
                .authorId(post.getAuthor().getId())
                .authorUsername(post.getAuthor().getUsername())
                .images(mapImages(post.getImages()))
                .likeCount(postLikeRepository.countByPost(post))
                .createdAt(post.getCreatedAt())
                .build();
    }

    private PostDetailResponse mapToDetailResponse(Post post) {
        return PostDetailResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .summary(post.getSummary())
                .detailContent(post.getDetailContent())
                .authorId(post.getAuthor().getId())
                .authorUsername(post.getAuthor().getUsername())
                .images(mapImages(post.getImages()))
                .likeCount(postLikeRepository.countByPost(post))
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    private List<PostImageResponse> mapImages(List<PostImage> images) {
        if (images == null || images.isEmpty()) {
            return Collections.emptyList();
        }

        return images.stream()
                .map(image -> PostImageResponse.builder()
                        .id(image.getId())
                        .imageUrl(image.getImageUrl())
                        .displayOrder(image.getDisplayOrder())
                        .build())
                .toList();
    }
}