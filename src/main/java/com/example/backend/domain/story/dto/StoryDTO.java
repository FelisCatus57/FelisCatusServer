package com.example.backend.domain.story.dto;

import com.example.backend.domain.story.entity.Story;
import com.example.backend.domain.user.entity.User;
import com.example.backend.global.image.Image;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class StoryDTO {

    private Long id;
    private Image image;
    private User user;

    public StoryDTO(Story story){
        this.id = story.getId();
        this.image = story.getImage();
        this.user = story.getUser();
    }


}
