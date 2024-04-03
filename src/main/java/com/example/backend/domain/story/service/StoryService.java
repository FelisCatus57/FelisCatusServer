package com.example.backend.domain.story.service;

import com.example.backend.domain.story.entity.Story;
import com.example.backend.domain.story.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;


    public List<Story> findAll(){
        return storyRepository.findAll();
    }

    public void deleteStory(long id){
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
        storyRepository.delete(story);
    }




}
