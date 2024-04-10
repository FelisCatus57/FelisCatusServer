package com.example.backend.domain.user.dto;

import com.example.backend.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Schema(description = "검색 유저 미니 응답")
public class SearchUserMiniResponse {

    @Schema(description = "유저 번호")
    private Long userId;

    @Schema(description = "유저 이름")
    private String name;

    @Schema(description = "유저 닉네임")
    private String nickname;

    @Schema(description = "유저 프로필 사진 경로")
    private String profileImgUrl;

    public SearchUserMiniResponse(User user) {
        this.userId = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.profileImgUrl = user.getImage().getImageUrl();
    }
}
