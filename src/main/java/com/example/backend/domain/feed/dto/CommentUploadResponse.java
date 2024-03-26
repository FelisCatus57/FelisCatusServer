package com.example.backend.domain.feed.dto;

import com.example.backend.domain.feed.entity.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

public class CommentUploadResponse {

    private Long commentId;

    private Long postId;

    private Long parentId;

    private UserResponseDTO userResponseDTO;

    private String content;

    private String createdDate;

    private List<CommentChildrenResponse> commentChildrenRespons;

    public CommentUploadResponse(Comment comment) {
        this.commentId = comment.getId();
        this.postId = comment.getPost().getId();
        if (comment.getParent() == null) {
            this.parentId = null;
        } else {
            this.parentId = comment.getParent().getId();
        }
        this.userResponseDTO = new UserResponseDTO(comment.getUser());
        this.content = comment.getContent();
        this.commentChildrenRespons = new CommentChildrenResponse().CommentParentResponse(comment.getChildren());
        this.createdDate = comment.getCreatedDate();
    }

}
