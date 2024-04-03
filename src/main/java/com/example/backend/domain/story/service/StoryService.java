package com.example.backend.domain.story.service;

import com.example.backend.domain.story.dto.StoryRequest;
import com.example.backend.domain.story.entity.Story;
import com.example.backend.domain.story.repository.StoryRepository;
import com.example.backend.domain.user.entity.User;
import com.example.backend.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final AuthUtil auth;
    private final StoryRepository storyRepository;


    public List<Story> findAll(){
        return storyRepository.findAll();
    }

    //story upload
    public void uploadStory(StoryRequest storyRequest){
        User storyUser = auth.getLoginUser();

    }
    //delete story
    public void deleteStory(long id){
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
        storyRepository.delete(story);
    }




}
