package com.semiarc.promethia.follow.repository;

import com.semiarc.promethia.follow.domain.Follow;
import com.semiarc.promethia.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerAndFollowing(User follower, User following);

    Optional<Follow> findByFollowerAndFollowing(User follower, User following);

    List<Follow> findAllByFollowerOrderByCreatedAtDesc(User follower);

    List<Follow> findAllByFollowingOrderByCreatedAtDesc(User following);

    List<Follow> findAllByFollower(User follower);
}