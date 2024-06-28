package com.example.backend.domain.story.dto;

import com.example.backend.domain.story.entity.Story;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "스토리 메뉴 미니 유저 응답")
public class StoryMenuResponse {

    @Schema(description = "유저 번호")
    private Long id;

    @Schema(description = "유저 닉네임")
    private String nickname;

    @Schema(description = "유저 이미지 경로")
    private String userImgUrl;

    @Builder
    public StoryMenuResponse(Story story) {
        this.id = story.getUser().getId();
        this.nickname = story.getUser().getNickname();
        this.userImgUrl = story.getUser().getImage().getImageUrl();
    }
}
