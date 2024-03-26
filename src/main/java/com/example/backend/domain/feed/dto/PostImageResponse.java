package com.example.backend.domain.feed.dto;

import com.example.backend.domain.feed.entity.PostImage;
import com.example.backend.global.image.Image;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
//@AllArgsConstructor
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "게시물 이미지 응답 DTO (리스트로 받기 때문에 for-each 사용)")
public class PostImageResponse {

    @Schema(description = "게시물 이미지 번호")
    private Long imageId;

    @Schema(description = "게시물 이미지")
    private Image image;

    @Builder
    public PostImageResponse(Long id, Image image) {
        this.imageId = id;
        this.image = image;
    }

    @Hidden
    public List<PostImageResponse> PostImageResponse(List<PostImage> postImages) {

        List<PostImageResponse> responses = new ArrayList<>();

        postImages.forEach(pi -> {

            PostImageResponse postImageResponse = new PostImageResponse(pi.getId(), pi.getImage());

            responses.add(postImageResponse);

        });

        return responses;
    }
}
