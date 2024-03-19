package com.example.backend.domain.feed.service;

import com.example.backend.domain.feed.dto.PostDTO;
import com.example.backend.domain.feed.dto.PostUploadRequest;
import com.example.backend.domain.feed.dto.PostUploadResponse;
import com.example.backend.domain.feed.entity.Post;
import com.example.backend.domain.feed.repository.PostRepository;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.ast.OpOr;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
