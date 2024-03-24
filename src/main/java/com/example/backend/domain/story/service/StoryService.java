package com.example.backend.domain.story.service;

import com.example.backend.domain.story.entity.Story;
import com.example.backend.domain.story.repository.StoryRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;

    @Autowired
    public StoryService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }
    //저장

    public void saveStory(Story story){
        storyRepository.save(story);
    }
    //삭제
    public void deleteStory(Story story){
        storyRepository.delete(story);
    }
   //유효기간 스토리 조회
    public List<Story> findActiveStory(){
       return storyRepository.findActiveStrory();
    }
    //id조회
    public Optional<Story> findStoryById(Long storyId){
        return storyRepository.findStoryById(storyId);
    }
}
