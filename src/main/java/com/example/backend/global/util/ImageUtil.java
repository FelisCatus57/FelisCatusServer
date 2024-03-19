package com.example.backend.global.util;

import com.example.backend.global.image.Image;
import com.example.backend.global.image.ImageType;
import com.google.common.base.Enums;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class ImageUtil {

    public static Image to(MultipartFile file) {

        final String originName = file.getOriginalFilename();
        final String name = FilenameUtils.getBaseName(originName);
        final String type = FilenameUtils.getExtension(originName).toUpperCase();

        // 확장자가 일치 하지 않으면
        if (!Enums.getIfPresent(ImageType.class, type).isPresent()) {
            throw new RuntimeException();
            //TODO 나중에 커스텀 Exception 넣기
        }

        return Image.builder()
                .imageType(ImageType.valueOf(type))
                .imageName(name)
                .imageUUID(UUID.randomUUID().toString())
                .build();
    }

    public static Image getBaseImage() {
        return Image.builder()
                .imageName("base")
                .imageType(ImageType.JPG)
                .imageUrl("http://uncertain.shop:9000/sample/base-UUID.jpg")
                .imageUUID("base-UUID")
                .build();
    }

}
