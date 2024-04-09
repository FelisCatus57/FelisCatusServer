package com.example.backend.domain.follow.dto;

import com.example.backend.domain.follow.entity.Follow;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(name = "팔로우 응답")
public class FollowResponse {

    @Schema(name = "팔로우 번호")
    private Long followId;

    public FollowResponse(Follow follow) {
        this.followId = follow.getId();
    }
}
