package com.example.backend.domain.feed.dto;

import com.example.backend.domain.feed.entity.PostLike;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "게시물 좋아요 유저 목록")
public class PostLikeUserResponse {

    @Schema(description = "좋아요 번호")
    private Long id;

    @Schema(description = "유저 번호")
    private Long userId;

    @Schema(description = "유저 닉네임")
    private String nickname;

    @Schema(description = "유저 이미지 경로    ")
    private String UserProfileUrl;

    public PostLikeUserResponse(PostLike postLike) {
        this.id = postLike.getId();
        this.userId = postLike.getUser().getId();
        this.nickname = postLike.getUser().getNickname();
        this.UserProfileUrl = postLike.getUser().getImage().getImageUrl();
    }
}
