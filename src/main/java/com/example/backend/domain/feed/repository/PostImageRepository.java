package com.example.backend.domain.feed.repository;

import com.example.backend.domain.feed.entity.Post;
import com.example.backend.domain.feed.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    List<PostImage> findAllByPost(Post post);

    List<PostImage> findAllByPostId(Long postId);


}
