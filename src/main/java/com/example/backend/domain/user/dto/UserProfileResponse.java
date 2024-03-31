package com.example.backend.domain.user.dto;

import com.example.backend.domain.feed.dto.PostResponse;
import com.example.backend.domain.user.entity.User;
import com.example.backend.global.image.Image;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "회원 조회 응답 DTO")
public class UserProfileResponse {

    @Schema(description = "유저 닉네임")
    private String UserNickname;

    @Schema(description = "유저 이름")
    private String UserRealName;

    @Schema(description = "유전 본인")
    private boolean isMe;

    @Schema(description = "유저 프로필 이미지")
    private Image UserImage; // 프로필 이미지

    @Schema(description = "유저 소개글")
    private String UserIntroduce;

    @Schema(description = "유저 웹사이트")
    private String UserWebsite; // 유저 웹사이트

    @Schema(description = "유저 게시글 수")
    private Long UserPostCount;

//    @Schema(description = "유저 팔로워 수")
//    private Long UserFollowerCount;
//
//    @Schema(description = "유저 팔로우 수")
//    private Long UserFollowCount;

    @Schema(description = "본인 게시글")
    private List<PostResponse> myPost;

    public UserProfileResponse(User user, List<PostResponse> posts) {
        this.UserNickname = user.getNickname();
        this.UserRealName = user.getName();
        this.isMe = false;
        this.UserImage = user.getImage();
        this.UserIntroduce = user.getIntroduce();
        this.UserWebsite = user.getWebsite();
        this.myPost = posts;
    }

}
