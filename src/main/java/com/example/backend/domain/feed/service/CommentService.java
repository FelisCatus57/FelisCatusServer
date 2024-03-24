package com.example.backend.domain.feed.service;

import com.example.backend.domain.feed.dto.CommentDTO;
import com.example.backend.domain.feed.dto.CommentUploadRequest;
import com.example.backend.domain.feed.entity.Comment;
import com.example.backend.domain.feed.entity.Post;
import com.example.backend.domain.feed.exception.PostNotExistedException;
import com.example.backend.domain.feed.repository.CommentRepository;
import com.example.backend.domain.feed.repository.PostRepository;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.EntityNotExistedException;
import com.example.backend.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void commentUpload(CommentUploadRequest commentUploadRequest) {

        // 현재 댓글을 적을 포스트를 가져온다.
        Post findPost = getPost(commentUploadRequest.getPostId());

        // 현재 로그인한 사용자를 가져온다.
        User loginUser = authUtil.getLoginUser();

        // 부모 댓글을 가져온다.
        Optional<Comment> parentComment = commentRepository.findCommentById(commentUploadRequest.getParentId());

        // 부모 댓글이 없으면 자신이 부모 댓글이다.
        boolean isParentComment = parentComment.isEmpty();

        if (isParentComment) { // 자신이 부모 댓글 이라면
            Comment saved = commentRepository.save(new Comment(loginUser, findPost, commentUploadRequest.getContent()));
        } else { // 자식 댓글 이라면
            Comment saved = commentRepository.save(new Comment(parentComment.get(), loginUser, findPost, commentUploadRequest.getContent()));
        }

    }

    private Post getPost(Long postId) {

        Post findPost = postRepository.findPostById(postId).orElseThrow(
                () -> new PostNotExistedException()
        );

        return findPost;
    }

}


















