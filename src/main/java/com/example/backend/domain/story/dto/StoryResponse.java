package com.example.backend.domain.story.dto;

import com.example.backend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoryResponse {
    //스토리 순서
    private Long id;

    private User user;
    //개인별 스토리 순서
    private Long storyId;

}
