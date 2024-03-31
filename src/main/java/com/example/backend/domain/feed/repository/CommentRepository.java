package com.example.backend.domain.feed.repository;

import com.example.backend.domain.feed.entity.Comment;
import com.example.backend.domain.feed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentById(Long commentId);

    List<Comment> findAllByPost(Post post);

    List<Comment> findAllByPostId(Long postId);

    List<Comment> findAllByParentId(Long parentId);

    List<Comment> findTop2ByPostIdOrderByCreatedDateDesc(Long postId);

    Long countAllByPostId(Long postId);
}
