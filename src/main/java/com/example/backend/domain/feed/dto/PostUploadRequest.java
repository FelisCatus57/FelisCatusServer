package com.example.backend.domain.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "게시물 업로드 요청 DTO")
public class PostUploadRequest {

    @Schema(description = "게시물 내용")
    private String content;

    @Schema(description = "게시물 이미지 (여러장 가능)")
    private List<MultipartFile> files = new ArrayList<>();
}
