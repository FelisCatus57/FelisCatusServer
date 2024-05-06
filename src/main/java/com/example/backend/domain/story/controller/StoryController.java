package com.example.backend.domain.story.controller;

import com.example.backend.domain.story.dto.StoryUploadRequest;
import com.example.backend.domain.story.dto.StoryUploadResponse;
import com.example.backend.domain.story.dto.StoryView;
import com.example.backend.domain.story.service.StoryService;
import com.example.backend.global.result.ResultCodeMessage;
import com.example.backend.global.result.ResultResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @PostMapping("/api/story")
    public ResponseEntity<ResultResponseDTO> uploadStory(@ModelAttribute StoryUploadRequest storyUploadRequest) {

        StoryUploadResponse storyUploadResponse = storyService.storyUpload(storyUploadRequest);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.STORY_SUCCESS, storyUploadResponse));
    }

    @DeleteMapping("/api/story/{storyId}")
    public ResponseEntity<ResultResponseDTO> deleteStory(@PathVariable("storyId") Long storyId) {

        storyService.deleteStory(storyId);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.STORY_DELETE_SUCCESS, storyId));
    }

    @GetMapping("/api/story")
    public ResponseEntity<ResultResponseDTO> getStory() {

        List<StoryView> storyViews = storyService.storyView();

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.STORY_VIEW_SUCCESS, storyViews));
    }

}
