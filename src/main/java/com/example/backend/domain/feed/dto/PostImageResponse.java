package com.example.backend.domain.feed.dto;

import com.example.backend.domain.feed.entity.PostImage;
import com.example.backend.global.image.Image;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "게시물 이미지 응답 DTO (리스트로 받기 때문에 foreach 사용)")
public class PostImageResponse {

    @Schema(description = "게시물 이미지 번호")
    private Long id;

    @Schema(description = "게시물 이미지")
    private Image image;

    public PostImageResponse(List<PostImage> postImages) {
         postImages.forEach(
                 pi -> {
                     this.id = pi.getId();
                     this.image = pi.getImage();
                 }
         );
    }
}
