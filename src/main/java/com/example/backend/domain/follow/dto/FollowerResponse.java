package com.example.backend.domain.follow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "팔로워 응답")
public class FollowerResponse {
    //TODO FollowingResponse 랑 형태 똑같아서 나중에 필요없는거 삭제

    @Schema(name = "팔로우 번호")
    private Long followId;

    @Schema(name = "팔로우 응답 유저 미니 객체")
    private FollowUserMiniResponse response;
    @Builder
    public FollowerResponse(Long followId, FollowUserMiniResponse response) {
        this.followId = followId;
        this.response = response;
    }
}
