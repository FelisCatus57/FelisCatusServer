package com.example.backend.domain.story.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "스토리 생성 응답 DTO")
public class StoryUploadResponse {

    @Schema(description = "스토리 번호")
    private Long storyId;

}
