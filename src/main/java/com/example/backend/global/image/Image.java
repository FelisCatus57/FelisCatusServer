package com.example.backend.global.image;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Objects;

@Getter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "이미지")
public class Image {

    @Schema(description = "Minio 에 저장된 이미지 URL")
    private String imageUrl;

    @Schema(description = "이미지 확장자")
    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @Schema(description = "이미지 원래 이름")
    private String imageName;

    @Schema(description = "이미지 UUID")
    private String imageUUID;

    @Hidden
    public void setUrl(String url) {
        this.imageUrl = url;
    }

    //
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Image image = (Image)obj;
        return Objects.equals(getImageUUID(), image.getImageUUID());
    }

}
