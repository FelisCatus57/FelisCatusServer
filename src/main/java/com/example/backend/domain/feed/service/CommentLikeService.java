package com.example.backend.domain.feed.service;

import com.example.backend.domain.feed.entity.Comment;
import com.example.backend.domain.feed.entity.CommentLike;
import com.example.backend.domain.feed.exception.CommentNotExistedException;
import com.example.backend.domain.feed.repository.CommentLikeRepository;
import com.example.backend.domain.feed.repository.CommentRepository;
import com.example.backend.domain.user.entity.User;
import com.example.backend.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final AuthUtil authUtil;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public void commentLike(Long commentId) {

        User findUser = authUtil.getLoginUser();

        Comment findComment = commentRepository.findCommentById(commentId).orElseThrow(
                () -> new CommentNotExistedException());

        CommentLike like = new CommentLike(findUser, findComment);

        commentLikeRepository.save(like);
    }

    @Transactional
    public void commentUnlike(Long commentId) {

        User findUser = authUtil.getLoginUser();

        Comment findComment = commentRepository.findCommentById(commentId).orElseThrow(
                () -> new CommentNotExistedException());

        commentLikeRepository.deleteByUserIdAndCommentId(findUser.getId(), findComment.getId());
    }
}
