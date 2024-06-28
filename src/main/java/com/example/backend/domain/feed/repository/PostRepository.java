package com.example.backend.domain.feed.repository;

import com.example.backend.domain.feed.entity.Post;
import com.example.backend.domain.feed.repository.QueryDSL.PostQueryRepository;
import com.example.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {

    Optional<Post> findPostById(Long postId);

    List<Post> findPostByUser(User user);

    Optional<List<Post>> findAllByUserId(Long userId);

    Optional<List<Post>> findAllByUserNickname(String nickname);

    Long countAllByUserNickname(String nickname);

}
