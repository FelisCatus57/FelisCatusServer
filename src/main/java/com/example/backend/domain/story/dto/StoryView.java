package com.example.backend.domain.story.dto;

import com.example.backend.domain.story.entity.Story;
import com.example.backend.domain.user.dto.MiniMenuUserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "스토리 조회 응답 DTO")
public class StoryView {

    @Schema(description = "스토리 번호")
    private Long id;

    @Schema(description = "스토리 이미지 경로")
    private String storyImageUrl;

    @Schema(description = "스토리 작성 유저 정보")
    private MiniMenuUserResponse userResponse;

    @Schema(description = "스토리 생성일")
    private String createdDate;

    @Schema(description = "스토리 수정일")
    private String modifiedDate;
    
    public StoryView(Story story) {
        this.id = story.getId();
        this.storyImageUrl = story.getImage().getImageUrl();
        this.userResponse = new MiniMenuUserResponse(story.getUser());
        this.createdDate = story.getCreatedDate();
        this.modifiedDate = story.getModifiedDate();
    }
}

