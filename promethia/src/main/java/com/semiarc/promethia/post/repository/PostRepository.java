package com.semiarc.promethia.post.repository;

import com.semiarc.promethia.post.domain.Post;
import com.semiarc.promethia.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByAuthorOrderByCreatedAtDesc(User author);

    List<Post> findAllByAuthorInOrderByCreatedAtDesc(List<User> authors);
}