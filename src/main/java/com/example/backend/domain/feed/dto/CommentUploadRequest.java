package com.example.backend.domain.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "부모 댓글 작성 요청 DTO")
public class CommentUploadRequest {

    @Schema(description = "댓글 내용")
    private String content;

}
