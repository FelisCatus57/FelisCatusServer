package com.example.backend.domain.feed.repository;

import com.example.backend.domain.feed.entity.Post;
import com.example.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findPostById(Long postId);

    Post findPostByUser(User user);


}
