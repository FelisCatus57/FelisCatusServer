package com.example.backend.domain.story.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(description = "스토리 업로드 DTO")
public class StoryUploadRequest {

    @Schema(description = "게시물 이미지")
    private MultipartFile file;
}
