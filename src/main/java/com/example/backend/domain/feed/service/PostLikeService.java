package com.example.backend.domain.feed.service;

import com.example.backend.domain.feed.entity.Post;
import com.example.backend.domain.feed.entity.PostLike;
import com.example.backend.domain.feed.exception.PostNotExistedException;
import com.example.backend.domain.feed.repository.PostLikeRepository;
import com.example.backend.domain.feed.repository.PostRepository;
import com.example.backend.domain.user.entity.User;
import com.example.backend.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final AuthUtil authUtil;
    private final PostRepository postRepository;

    @Transactional
    public void postLike(Long postId) {

        User findUser = authUtil.getLoginUser();

        Post findPost = postRepository.findPostById(postId).orElseThrow(
                () -> new PostNotExistedException());

        PostLike like = new PostLike(findUser, findPost);

        postLikeRepository.save(like);
    }

    @Transactional
    public void postUnlike(Long postId) {

        Long loginUserId = authUtil.getLoginUserId();

        Long findPostId = postRepository.findPostById(postId).orElseThrow(
                () -> new PostNotExistedException()).getId();


        postLikeRepository.deleteByUserIdAndPostId(loginUserId, findPostId);
    }
}
