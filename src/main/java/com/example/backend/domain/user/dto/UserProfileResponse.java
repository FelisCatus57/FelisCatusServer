package com.example.backend.domain.user.dto;

import com.example.backend.global.image.Image;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 조회 응답 DTO")
public class UserProfileResponse {

    @Schema(description = "유저 닉네임")
    private String UserNickname;

    @Schema(description = "유저 이름")
    private String UserRealName;

    @Schema(description = "유저 프로필 이미지")
    private Image UserImage; // 프로필 이미지

    @Schema(description = "유저 소개글")
    private String UserIntroduce;

    @Schema(description = "유저 웹사이트")
    private String UserWebsite; // 유저 웹사이트

    @Schema(description = "유저 게시글 수")
    private Long UserPostCount;

}
