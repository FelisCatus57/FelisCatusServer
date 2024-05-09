package com.example.backend.domain.story.controller;

import com.example.backend.domain.story.service.StoryViewUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoryViewUserController {

    private final StoryViewUserService storyViewUserService;
    
}
