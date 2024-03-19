package com.example.backend.domain.user.dto;

import com.example.backend.global.image.Image;

public class UserProfileResponse {

    private String UserNickname; // 유저 닉네임

    private String UserRealName; // 유저 이름

    private Image UserImage; // 프로필 이미지

    private String UserIntroduce; // 유저 소개글

    private String UserWebsite; // 유저 웹사이트

    private Long UserPostCount; // 게시물 수

}
