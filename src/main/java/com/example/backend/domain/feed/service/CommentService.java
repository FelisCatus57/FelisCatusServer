package com.example.backend.domain.feed.service;

import com.example.backend.domain.feed.dto.CommentUploadRequest;
import com.example.backend.domain.feed.dto.CommentResponse;
import com.example.backend.domain.feed.entity.Comment;
import com.example.backend.domain.feed.entity.Post;
import com.example.backend.domain.feed.exception.CommentNotExistedException;
import com.example.backend.domain.feed.exception.PostCanNotDeleteException;
import com.example.backend.domain.feed.exception.PostNotExistedException;
import com.example.backend.domain.feed.repository.CommentRepository;
import com.example.backend.domain.feed.repository.PostRepository;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponse commentUpload(CommentUploadRequest commentUploadRequest, Long postId) {

        // 현재 댓글을 적을 포스트를 가져온다.
        Post findPost = getPost(postId);

        // 현재 로그인한 사용자를 가져온다.
        User loginUser = authUtil.getLoginUser();

        Comment saved = commentRepository.save(new Comment(loginUser, findPost, commentUploadRequest.getContent()));

        return new CommentResponse(saved);
    }

    @Transactional
    public CommentResponse childrenCommentUpload(CommentUploadRequest commentUploadRequest, Long postId, Long commentId) {

        // 현재 댓글을 적을 포스트를 가져온다.
        Post findPost = getPost(postId);

        // 현재 로그인한 사용자를 가져온다.
        User loginUser = authUtil.getLoginUser();

        // 부모 댓글을 가져온다.
        Comment parentComment = getParentComment(commentId);

        Comment saved = commentRepository.save(new Comment(parentComment, loginUser, findPost, commentUploadRequest.getContent()));

        return new CommentResponse(saved);
    }

    @Transactional
    public void deleteComment(Long commentId) {

        // 로그인한 유저를 가져온다.
        User loginUser = authUtil.getLoginUser();

        // 해당 된 게시물을 가져온다.
        Comment findComment = getComment(commentId);

        if (!findComment.getUser().getId().equals(loginUser.getId()))
            throw new PostCanNotDeleteException();

        // 자식 댓글이라면
        commentRepository.delete(findComment);
    }

    public List<CommentResponse> getAllCommentByPostId(Long postId) {

        List<CommentResponse> commentResponses = new ArrayList<>();

        List<Comment> allByPostId = commentRepository.findAllByPostId(postId);

        allByPostId.forEach(
                comment -> commentResponses.add(new CommentResponse(comment))
        );

        return commentResponses;
    }

    public Long getPostCommentCount(Long postId) {
        return commentRepository.countAllByPostId(postId);
    }

    private Comment getComment(Long commentId) {

        Comment findComment = commentRepository.findCommentById(commentId).orElseThrow(
                () -> new CommentNotExistedException());

        return findComment;
    }

    private Comment getParentComment(Long commentId) {

        Comment findComment = commentRepository.findCommentById(commentId).orElseThrow(
                () -> new CommentNotExistedException());

        return findComment;
    }

    private Post getPost(Long postId) {

        Post findPost = postRepository.findPostById(postId).orElseThrow(
                () -> new PostNotExistedException()
        );

        return findPost;
    }

}


















