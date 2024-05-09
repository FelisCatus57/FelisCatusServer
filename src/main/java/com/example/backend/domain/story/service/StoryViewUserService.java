package com.example.backend.domain.story.service;

import com.example.backend.domain.story.dto.StoryViewUserResponse;
import com.example.backend.domain.story.entity.Story;
import com.example.backend.domain.story.entity.StoryViewUser;
import com.example.backend.domain.story.repository.StoryViewUserRepository;
import com.example.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<StoryViewUserResponse> getStoryViewWithStory(Story story) {

        List<StoryViewUserResponse> res = new ArrayList<>();

        List<StoryViewUser> findAll = storyViewUserRepository.findAllByStory(story);

        findAll.stream().forEach(
                storyViewUser -> {
                    res.add(new StoryViewUserResponse(storyViewUser));
                });
        return res;
    }

}
