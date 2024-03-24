package com.example.backend.domain.feed.service;

import com.example.backend.domain.feed.dto.PostUploadRequest;
import com.example.backend.domain.feed.dto.PostUploadResponse;
import com.example.backend.domain.feed.entity.Post;
import com.example.backend.domain.feed.repository.PostRepository;
import com.example.backend.domain.user.entity.User;
import com.example.backend.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final AuthUtil authUtil;
    private final PostImageService postImageService;
    private final PostRepository postRepository;


    @Transactional
    public PostUploadResponse postUpload(PostUploadRequest postUploadRequest) {

        // 현재 로그인한 유저정보를 가져온다.
        User loginUser = authUtil.getLoginUser();

//        PostDTO postDTO = new PostDTO(loginUser, postUploadRequest.getContent());
        Post post = new Post(loginUser, postUploadRequest.getContent());

        Post save = postRepository.save(post);

        postImageService.saveAll(save, postUploadRequest.getFiles());

        return new PostUploadResponse(save.getId());

    }



}
