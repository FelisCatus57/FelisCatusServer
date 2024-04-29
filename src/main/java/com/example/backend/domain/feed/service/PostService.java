package com.example.backend.domain.feed.service;

import com.example.backend.domain.feed.dto.*;
import com.example.backend.domain.feed.entity.Post;
import com.example.backend.domain.feed.entity.PostLike;
import com.example.backend.domain.feed.exception.PostCanNotDeleteException;
import com.example.backend.domain.feed.exception.PostCantUpdateException;
import com.example.backend.domain.feed.exception.PostNotExistedException;
import com.example.backend.domain.feed.repository.CommentRepository;
import com.example.backend.domain.feed.repository.PostLikeRepository;
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
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;


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

    public PostResponse modifyPost(Long postId, PostEditRequest postEditRequest) {

        Long loginUserId = authUtil.getLoginUserId();

        Post findPost = getPost(postId);

        Long postCommentCount = commentService.getPostCommentCount(postId);

        if (!findPost.getUser().getId().equals(loginUserId)) {
            new PostCantUpdateException();
        }

        findPost.updateContent(postEditRequest.getContent().isEmpty() ? findPost.getContent() : postEditRequest.getContent());

        Post save = postRepository.save(findPost);

        return new PostResponse(save, postCommentCount);
    }

    public Long getUserPostCount(String nickname) {
        return postRepository.countAllByUserNickname(nickname);
    }

    public void getFollowUserPost(Long followingId) {
        User loginUser = authUtil.getLoginUser();
        
    }


    public List<PostResponse> getUserFollowingPost() {

        String loginUserNickname = authUtil.getLoginUserNickname();

        List<PostResponse> responses = new ArrayList<>();
        List<Post> posts = postRepository.findAllByFollowingPost(loginUserNickname);

        posts.forEach(
                post -> {
                    Long postCommentCount = commentService.getPostCommentCount(post.getId());
                    responses.add(new PostResponse(post, postCommentCount));
                }
        );
        return responses;
    }

    public List<PostResponse> getUserNotFollowingPost() {

        String loginUserNickname = authUtil.getLoginUserNickname();

        List<PostResponse> responses = new ArrayList<>();
        List<Post> posts = postRepository.findAllByNotFollowingPost(loginUserNickname);

        posts.forEach(
                post -> {
                    Long postCommentCount = commentService.getPostCommentCount(post.getId());
                    responses.add(new PostResponse(post, postCommentCount));
                }
        );
        return responses;
    }

    public List<PostLikeUserResponse> getPostLikeUser(Long postId) {
        List<PostLikeUserResponse> responses = new ArrayList<>();

        List<PostLike> likes = postLikeRepository.findByPostId(postId);

        likes.forEach(
                like -> {
                    responses.add(new PostLikeUserResponse(like));
                }
        );

        return responses;
    }


}
