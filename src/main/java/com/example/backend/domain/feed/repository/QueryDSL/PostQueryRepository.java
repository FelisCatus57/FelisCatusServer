package com.example.backend.domain.feed.repository.QueryDSL;

import com.example.backend.domain.feed.entity.Post;

import java.util.List;

public interface PostQueryRepository {

    List<Post> findAllByFollowingPost(String nickname);

    List<Post> findAllByNotFollowingPost(String nickname);
}
