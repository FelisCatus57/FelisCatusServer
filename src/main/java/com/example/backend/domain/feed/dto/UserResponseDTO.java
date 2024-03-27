package com.example.backend.domain.feed.dto;

import com.example.backend.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Schema(description = "유저 정보 응답 DTO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDTO {

    @Schema(description = "유저 번호")
    private Long userId;

    @Schema(description = "유저 아이디")
    private String username;

    @Schema(description = "유저 닉네임")
    private String nickname;

    @Schema(description = "유저 이미지 정보")
    private String userProfileUrl;

    public UserResponseDTO(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.userProfileUrl = user.getImage().getImageUrl();
    }
}
