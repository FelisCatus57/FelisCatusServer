package com.example.backend.domain.feed.dto;

import com.example.backend.domain.feed.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "포스트 요청시 응답 DTO")
public class PostResponse {

    @Schema(description = "게시물 번호")
    private Long postId;

    @Schema(description = "유저 정보")
    private UserFeedResponse userFeedResponse;

    @Schema(description = "게시물 내용")
    private String content;

    @Schema(description = "게시물 이미지")
    private List<PostImageResponse> postImageResponse; // 한개가 아니라 여러개

    @Schema(description = "게시물 생성 날짜")
    private String createdDate;

    @Schema(description = "게시물에 달린 댓글 총 개수")
    private Long commentCount;

    public PostResponse(Post post, Long commentCount) {
        this.postId = post.getId();
        this.content = post.getContent();
        this.userFeedResponse = new UserFeedResponse(post.getUser());
        this.postImageResponse = new PostImageResponse().PostImageResponse(post.getImages());
        this.createdDate = post.getCreatedDate();
        this.commentCount = commentCount;
    }

}
