package com.example.backend.domain.story.dto;

import com.example.backend.domain.story.entity.Story;
import com.example.backend.domain.user.dto.MiniMenuUserResponse;
import lombok.Data;

@Data
public class StoryView {

    private Long id;

    private String storyImageUrl;

    private MiniMenuUserResponse userResponse;

    private String createdDate;

    private String modifiedDate;

    public StoryView(Story story) {
        this.id = story.getId();
        this.storyImageUrl = story.getImage().getImageUrl();
        this.userResponse = new MiniMenuUserResponse(story.getUser());
        this.createdDate = story.getCreatedDate();
        this.modifiedDate = story.getModifiedDate();
    }
}

