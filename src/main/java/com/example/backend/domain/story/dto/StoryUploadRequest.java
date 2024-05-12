package com.example.backend.domain.story.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Schema(description = "스토리 업로드 DTO")
public class StoryUploadRequest {

    @Schema(description = "게시물 이미지")
    private List<MultipartFile> files;
//    private MultipartFile file;
}
