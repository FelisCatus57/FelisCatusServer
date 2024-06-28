package com.example.backend.domain.story.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "스토리 생성 응답 DTO")
public class StoryUploadResponse {

    @Schema(description = "스토리 번호")
    private List<Long> storyIds;

    @Builder
    public StoryUploadResponse(List<Long> storyIds) {
        this.storyIds = storyIds;
    }

}
