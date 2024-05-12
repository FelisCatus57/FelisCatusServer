package com.example.backend.domain.story.service;

import com.example.backend.domain.story.dto.StoryViewUserResponse;
import com.example.backend.domain.story.entity.Story;
import com.example.backend.domain.story.entity.StoryViewUser;
import com.example.backend.domain.story.exception.StoryNotExistException;
import com.example.backend.domain.story.repository.StoryRepository;
import com.example.backend.domain.story.repository.StoryViewUserRepository;
import com.example.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryViewUserService {

    private final StoryRepository storyRepository;
    private final StoryViewUserRepository storyViewUserRepository;
    
    // 추가
    public void addViewUser(Story story, User user) {
        StoryViewUser storyViewUser = new StoryViewUser(story, user);
        storyViewUserRepository.save(storyViewUser);
    }

    @Transactional
    public List<StoryViewUserResponse> getStoryViewWithStory(Long storyId) {

        List<StoryViewUserResponse> res = new ArrayList<>();

        Story findStory = storyRepository.findById(storyId).orElseThrow(
                () -> new StoryNotExistException());

        List<StoryViewUser> findAll = storyViewUserRepository.findAllByStory(findStory);

        long viewCount = findAll.stream().count();

        findAll.stream().forEach(
                storyViewUser -> {
                    StoryViewUserResponse storyViewUserResponse = new StoryViewUserResponse(storyViewUser);
                    storyViewUserResponse.setViewCount(viewCount);
                    res.add(storyViewUserResponse);
                });

        return res;
    }

}
