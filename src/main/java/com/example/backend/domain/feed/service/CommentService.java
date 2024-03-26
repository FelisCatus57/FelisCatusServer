package com.example.backend.domain.feed.service;

import com.example.backend.domain.feed.dto.CommentUploadRequest;
import com.example.backend.domain.feed.dto.CommentUploadResponse;
import com.example.backend.domain.feed.entity.Comment;
import com.example.backend.domain.feed.entity.Post;
import com.example.backend.domain.feed.exception.PostNotExistedException;
import com.example.backend.domain.feed.repository.CommentRepository;
import com.example.backend.domain.feed.repository.PostRepository;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentUploadResponse commentUpload(CommentUploadRequest commentUploadRequest, Long postId) {

        // 현재 댓글을 적을 포스트를 가져온다.
        Post findPost = getPost(postId);

        // 현재 로그인한 사용자를 가져온다.
        User loginUser = authUtil.getLoginUser();

        if (commentUploadRequest.getParentId() == null) {
            Comment saved = commentRepository.save(new Comment(loginUser, findPost, commentUploadRequest.getContent()));

            return new CommentUploadResponse(saved);
        } else {

            Optional<Comment> parentComment = commentRepository.findCommentById(commentUploadRequest.getParentId());

            Comment saved = commentRepository.save(new Comment(parentComment.get(), loginUser, findPost, commentUploadRequest.getContent()));

            return new CommentUploadResponse(saved);

        }
    }

    private Post getPost(Long postId) {

        Post findPost = postRepository.findPostById(postId).orElseThrow(
                () -> new PostNotExistedException()
        );

        return findPost;
    }

}


















