package com.example.backend.domain.follow.dto;

import com.example.backend.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "팔로우용 미니 유저 응답")
public class FollowUserMiniResponse {

    @Schema(description = "유저 번호")
    private Long userId;

    @Schema(description = "유저 닉네임")
    private String nickname;

    @Schema(description = "유저 프로필 사진 경로")
    private String profileImgUrl;

    public FollowUserMiniResponse(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.profileImgUrl = user.getImage().getImageUrl();
    }
}
