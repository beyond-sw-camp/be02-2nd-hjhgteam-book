package com.example.demo.follow.repository;

import com.example.demo.follow.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query(value = "SELECT COUNT(*) FROM FOLLOW WHERE follower_id = :follower_id AND following_id = :following_id", nativeQuery = true)
    int followState(Long follower_id, Long following_id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM FOLLOW WHERE follower_id = :follower_id AND following_id = :following_id", nativeQuery = true)
    void unFollow(Long follower_id, Long following_id);

    @Query(value = "SELECT following_id FROM FOLLOW WHERE follower_id = :userId ", nativeQuery = true)
    List<Long> followings(Long userId);

    @Query(value = "SELECT follower_id FROM FOLLOW WHERE following_id = :userId", nativeQuery = true)
    List<Long> followers(Long userId);

}
