package com.example.backend.domain.story.service;

import com.example.backend.domain.story.repository.StoryVisitorRepository;
import com.example.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoryVisiterService {
    private final StoryVisitorRepository storyVisitorRepository;
    private final User user;
    

}
