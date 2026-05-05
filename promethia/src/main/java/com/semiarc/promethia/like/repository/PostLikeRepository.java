package com.semiarc.promethia.like.repository;

import com.semiarc.promethia.like.domain.PostLike;
import com.semiarc.promethia.post.domain.Post;
import com.semiarc.promethia.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostAndUser(Post post, User user);

    Optional<PostLike> findByPostAndUser(Post post, User user);

    long countByPost(Post post);
}