package com.example.backend.domain.feed.dto;

import com.example.backend.domain.feed.entity.Post;
import com.example.backend.domain.feed.entity.PostImage;
import com.example.backend.domain.user.dto.UserDTO;
import com.example.backend.global.image.Image;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "포스트 요청시 응답 DTO")
public class PostResponse {

    @Schema(description = "게시물 번호")
    private Long id;

    @Schema(description = "유저 정보")
    private  PostUserResponse postUserResponse;

    @Schema(description = "게시물 내용")
    private String content;

    @Schema(description = "게시물 이미지")
    private PostImageResponse postImageResponse;

    @Schema(description = "게시물 생성 날짜")
    private LocalDateTime createAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.postUserResponse = new PostUserResponse(post.getUser());
        this.postImageResponse = new PostImageResponse(post.getImages());
        this.createAt = post.getCreatedDate();

    }
}
