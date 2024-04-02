package com.example.backend.domain.feed.service;

import com.example.backend.domain.feed.dto.PostResponse;
import com.example.backend.domain.feed.dto.PostUploadRequest;
import com.example.backend.domain.feed.dto.PostUploadResponse;
import com.example.backend.domain.feed.entity.Post;
import com.example.backend.domain.feed.exception.PostCanNotDeleteException;
import com.example.backend.domain.feed.exception.PostNotExistedException;
import com.example.backend.domain.feed.repository.PostRepository;
import com.example.backend.domain.user.entity.User;
import com.example.backend.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final AuthUtil authUtil;
    private final PostImageService postImageService;
    private final PostRepository postRepository;
    private final CommentService commentService;


    // 게시물 업로드
    @Transactional
    public PostUploadResponse postUpload(PostUploadRequest postUploadRequest) {

        // 현재 로그인한 유저정보를 가져온다.
        User loginUser = authUtil.getLoginUser();

        Post post = new Post(loginUser, postUploadRequest.getContent());
 
        Post save = postRepository.save(post);

        postImageService.saveAll(save, postUploadRequest.getFiles());

        return new PostUploadResponse(save.getId());

    }

    @Transactional
    public void deletePost(Long postId) {

        // 로그인한 유저를 가져온다.
        User loginUser = authUtil.getLoginUser();

        // 해당 된 게시물을 가져온다.
        Post findPost = getPost(postId);

        if (!findPost.getUser().getId().equals(loginUser.getId()))
            throw new PostCanNotDeleteException();

        postImageService.deleteAll(findPost);

        postRepository.delete(findPost);

    }

    @Transactional
    public List<PostResponse> getAllPostByUserNickname(String nickname) {

        List<PostResponse> postResponses = new ArrayList<>();

        List<Post> posts = postRepository.findAllByUserNickname(nickname).orElseThrow(
                () -> new PostNotExistedException());

        for (Post post : posts) {
            Long postCommentCount = commentService.getPostCommentCount(post.getId());
            postResponses.add(new PostResponse(post, postCommentCount));
        }

        return postResponses;
    }

    private Post getPost(Long postId) {
        Post getPost = postRepository.findPostById(postId).orElseThrow(
                () -> new PostNotExistedException());

        return getPost;

    }


    // 특정 유저의 멤버 번호를 통해 해당 유저의 모든 게시물 조회
    public List<PostResponse> getUserAllPost(Long userId) {

        List<PostResponse> responses = new ArrayList<>();

        for (Post post : postRepository.findAllByUserId(userId).orElseThrow(
                () -> new PostNotExistedException()
        )) {
            Long postCommentCount = commentService.getPostCommentCount(post.getId());

            responses.add(new PostResponse(post, postCommentCount));
        }
        return responses;
    }

    public Long getUserPostCount(String nickname) {
        return postRepository.countAllByUserNickname(nickname);
    }



}
