package com.example.backend.domain.feed.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUploadRequest {

    // 게시물 번호
    private Long postId;

    // 댓글 부모 
    private Long parentId;

    // 댓글 내용
    private String content;

}
