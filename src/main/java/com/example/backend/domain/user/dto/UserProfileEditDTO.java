package com.example.backend.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "유저 정보수정 요청 DTO")
public class UserProfileEditDTO {

    @Schema(description = "유저 번호")
    private Long id;

    @Schema(description = "유저 아이디")
    private String username;

    @Schema(description = "유저 이름")
    private String name;

    @Schema(description = "유저 닉네임")
    private String nickname;

//    private String
}
