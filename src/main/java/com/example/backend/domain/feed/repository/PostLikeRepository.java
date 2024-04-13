package com.example.backend.domain.feed.repository;

import com.example.backend.domain.feed.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    List<PostLike> findByPostId(Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);
}
