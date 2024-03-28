package com.example.backend.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "회원가입 요청 DTO")
public class UserRegisterRequest {

    @Schema(description = "유저 아이디", example = "test")
    private String username;

    @Schema(description = "유저 비밀번호", example = "test123")
    private String password;

    @Schema(description = "유저 이름", example = "홍길동")
    private String name;

    @Schema(description = "유저 닉네임", example = "gildong")
    private String nickname;

    @Schema(description = "유저 이메일", example = "gildong@test.com")
    private String email;
}
