package com.example.backend.domain.user.dto;

import com.example.backend.domain.user.entity.User;
import com.example.backend.global.image.Image;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "유저 DTO 변환")
public class UserDTO {

    @Schema(description = "유저 번호")
    private Long id;

    @Schema(description = "유저 아이디")
    private String username;

    @Schema(description = "유저 이름")
    private String name;

    @Schema(description = "유저 닉네임")
    private String nickname;

    @Schema(description = "유저 이메일")
    private String email;

    @Schema(description = "이미지")
    private Image image;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.image = user.getImage();
    }
}
