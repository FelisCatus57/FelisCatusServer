package com.example.backend.domain.feed.dto;

import com.example.backend.domain.feed.entity.Comment;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "댓글 요청시 자식 댓글 응답 DTO")
public class CommentChildrenResponse {

    @Schema(description = "자식 댓글 번호")
    private Long commentId;

    @Schema(description = "자식 댓글 내용")
    private String content;

    @Schema(description = "자식 댓글 생성날짜")
    private String createdDate;

    @Schema(description = "자식 댓글 유저 정보")
    private UserFeedResponse userFeedResponse;

    @Hidden
    public List<CommentChildrenResponse> CommentParentResponse(List<Comment> children) {

        List<CommentChildrenResponse> responses = new ArrayList<>();

        children.forEach( ch -> {

            CommentChildrenResponse CommentChildrenResponse = new CommentChildrenResponse(ch.getId(), ch.getContent(), ch.getCreatedDate(), new UserFeedResponse(ch.getUser()));

            responses.add(CommentChildrenResponse);

        });

        return responses;
    }

}
