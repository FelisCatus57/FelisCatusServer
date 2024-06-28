package com.example.backend.domain.story.controller;

import com.example.backend.domain.story.dto.StoryMenuResponse;
import com.example.backend.domain.story.dto.StoryUploadRequest;
import com.example.backend.domain.story.dto.StoryUploadResponse;
import com.example.backend.domain.story.dto.StoryView;
import com.example.backend.domain.story.service.StoryService;
import com.example.backend.global.result.ResultCodeMessage;
import com.example.backend.global.result.ResultResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@ResponseBody
@RestController
@RequiredArgsConstructor
@Tag(name = "Story", description = "스토리 API")
public class StoryController {

    private final StoryService storyService;

    @Operation(summary = "스토리 작성", description = "스토리 작성하기")
    @PostMapping("/api/stories")
    public ResponseEntity<ResultResponseDTO> uploadStory(@ModelAttribute StoryUploadRequest storyUploadRequest) {

        StoryUploadResponse storyUploadResponse = storyService.storyUpload(storyUploadRequest);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.STORY_SUCCESS, storyUploadResponse));
    }

    @Operation(summary = "스토리 삭제", description = "스토리 삭제하기")
    @Parameter(name = "storyId", description = "스토리 번호", required = true)
    @DeleteMapping("/api/stories/{storyId}")
    public ResponseEntity<ResultResponseDTO> deleteStory(@PathVariable("storyId") Long storyId) {

        storyService.deleteStory(storyId);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.STORY_DELETE_SUCCESS, storyId));
    }
    
    @Operation(summary = "스토리 작성 유저 목록 조회", description = "나의 팔로우 리스트에서 스토리를 올린 유저들 목록 조회")
    @GetMapping("/api/stories")
    public ResponseEntity<ResultResponseDTO> getStoryList() {

        Set<StoryMenuResponse> storyMenuResponses = storyService.storyViewList();

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.STORY_LIST_VIEW_SUCCESS, storyMenuResponses));
    }

    @Operation(summary = "스토리 조회", description = "해당 스토리를 올린 유저 스토리 모아서 조회")
    @Parameter(name = "userId", description = "유저 번호", required = true)
    @GetMapping("/api/{userId}/stories")
    public ResponseEntity<ResultResponseDTO> storyView(@PathVariable("userId") Long userId) {

        List<StoryView> storyViews = storyService.storyView(userId);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.STORY_VIEW_SUCCESS, storyViews));
    }

}
