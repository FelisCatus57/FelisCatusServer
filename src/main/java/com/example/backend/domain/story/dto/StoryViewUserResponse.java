package com.example.backend.domain.story.dto;

import com.example.backend.domain.story.entity.StoryViewUser;
import com.example.backend.domain.user.dto.MiniMenuUserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "스토리를 읽은 유저 응답 DTO")
public class StoryViewUserResponse {

    @Schema(description = "스토리 조회 유저 번호")
    private Long id;
    
    @Schema(description = "스토리 조회 유저 정보")
    private MiniMenuUserResponse miniMenuUserResponse;

    @Schema(description = "스토리 조회 유저 수")
    private Long viewCount;

    public StoryViewUserResponse(StoryViewUser storyViewUser) {
        this.id = storyViewUser.getId();
        this.miniMenuUserResponse = new MiniMenuUserResponse(storyViewUser.getUser());
    }
}
