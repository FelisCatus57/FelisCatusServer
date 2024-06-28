package com.example.backend.domain.user.dto;

import com.example.backend.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "메뉴 유저 응답")
public class MiniMenuUserResponse {

    @Schema(description = "유저 ID(PK)")
    private Long id;

    @Schema(description = "유저 이름")
    private String name;

    @Schema(description = "유저 닉네임")
    private String nickname;

    @Schema(description = "유저 프로필 이미지 URL")
    private String profileImageUrl;

    public MiniMenuUserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.profileImageUrl = user.getImage().getImageUrl();
    }

}
