package com.example.backend.domain.story.dto;


import com.example.backend.domain.user.entity.User;
import com.example.backend.global.image.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoryRequest {

    private Long id;
    private User user;
    private Image image;

    private List<MultipartFile> files = new ArrayList<>();

}
