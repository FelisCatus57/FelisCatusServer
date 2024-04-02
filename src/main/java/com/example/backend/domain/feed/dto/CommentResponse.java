package com.example.backend.domain.feed.dto;

import com.example.backend.domain.feed.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "댓글 작성 응답 DTO")
public class CommentUploadResponse {

    @Schema(description = "댓글 번호")
    private Long commentId;
    
    @Schema(description = "게시물 번호")
    private Long postId;

    @Schema(description = "부모 댓글 번호")
    private Long parentId;

    @Schema(description = "댓글 작성한 유저 정보")
    private UserFeedResponse userFeedResponse;

    @Schema(description = "댓글 내용")
    private String content;

    @Schema(description = "댓글 작성 날짜/시간")
    private String createdDate;

    @Schema(description = "자식 댓글")
    private List<CommentChildrenResponse> commentChildrenRespons;

    public CommentUploadResponse(Comment comment) {
        this.commentId = comment.getId();
        this.postId = comment.getPost().getId();
        if (comment.getParent() == null) {
            this.parentId = null;
        } else {
            this.parentId = comment.getParent().getId();
        }
        this.userFeedResponse = new UserFeedResponse(comment.getUser());
        this.content = comment.getContent();
        this.commentChildrenRespons = new CommentChildrenResponse().CommentParentResponse(comment.getChildren());
        this.createdDate = comment.getCreatedDate();
    }

}
