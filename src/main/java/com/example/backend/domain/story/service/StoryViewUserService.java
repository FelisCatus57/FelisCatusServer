package com.example.backend.domain.story.service;

import com.example.backend.domain.story.entity.Story;
import com.example.backend.domain.story.entity.StoryViewUser;
import com.example.backend.domain.story.repository.StoryViewUserRepository;
import com.example.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryViewUserService {

    private final StoryViewUserRepository storyViewUserRepository;
    
    // 추가
    public void addViewUser(Story story, User user) {
        StoryViewUser storyViewUser = new StoryViewUser(story, user);
        storyViewUserRepository.save(storyViewUser);
    }

    //TODO 조회 부분 수정하기

    // 조회
    public List<StoryViewUser> getStoryViewWithUser(User user) {
        return storyViewUserRepository.findAllByUser(user);
    }

    // 조회 2
    public List<StoryViewUser> getStoryViewWithStory(Story story) {
        return storyViewUserRepository.findAllByStory(story);
    }

    // 조회 3
    public List<StoryViewUser> getStoryViewWithUserAndStory(Story story, User user) {
        return storyViewUserRepository.findAllByStoryAndUser(story, user);
    }

}
