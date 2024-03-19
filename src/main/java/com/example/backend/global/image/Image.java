package com.example.backend.global.image;

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
public class Image {

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    private String imageName;

    private String imageUUID;

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
