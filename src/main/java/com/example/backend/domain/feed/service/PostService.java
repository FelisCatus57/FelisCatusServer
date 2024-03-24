package com.example.backend.domain.feed.service;

import com.example.backend.domain.feed.dto.PostDTO;
import com.example.backend.domain.feed.dto.PostUploadRequest;
import com.example.backend.domain.feed.entity.Post;
import com.example.backend.domain.feed.repository.PostRepository;
import com.example.backend.domain.user.entity.User;
import com.example.backend.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final AuthUtil authUtil;
    private final PostImageService postImageService;
    private final PostRepository postRepository;


    public void postUpload(PostUploadRequest postUploadRequest) {

        User loginUser = authUtil.getLoginUser();

        PostDTO postDTO = new PostDTO(loginUser, postUploadRequest.getContent());

        Post save = postRepository.save(postDTO.toEntity());

        postImageService.saveAll(save, postUploadRequest.getFiles());


    }



}
