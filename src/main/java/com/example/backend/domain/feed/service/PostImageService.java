package com.example.backend.domain.feed.service;

import com.example.backend.common.minio.MinioUploader;
import com.example.backend.domain.feed.entity.Post;
import com.example.backend.domain.feed.entity.PostImage;
import com.example.backend.domain.feed.repository.PostImageRepository;
import com.example.backend.global.image.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostImageService {

    private final MinioUploader minioUploader;
    private final PostImageRepository postImageRepository;


    @Transactional
    public void saveAll(Post post, List<MultipartFile> multipartFiles) {

        List<Image> images = multipartFiles.stream()
                .map(i -> minioUploader.to(i, "post"))
                .collect(Collectors.toList());


        images.forEach( image -> {
            PostImage postImage = PostImage.builder()
                    .post(post)
                    .image(image)
                    .build();
            postImageRepository.save(postImage);
        });
    }

    @Transactional
    public void deleteAll(Post post) {

        List<PostImage> allPosts = postImageRepository.findAllByPost(post);

        allPosts.forEach( image -> minioUploader.deleteImage(image.getImage(), "post"));

        postImageRepository.deleteAllInBatch(allPosts);
    }


}
