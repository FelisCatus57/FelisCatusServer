package com.example.backend.domain.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "게시물 수정 요청")
public class PostEditRequest {

    @Schema(description = "게시글 수정 내용")
    private String content;

}
