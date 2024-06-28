package com.example.backend.domain.story.controller;

import com.example.backend.domain.story.dto.StoryViewUserResponse;
import com.example.backend.domain.story.service.StoryViewUserService;
import com.example.backend.global.result.ResultCodeMessage;
import com.example.backend.global.result.ResultResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ResponseBody
@RestController
@RequiredArgsConstructor
@Tag(name = "Story", description = "스토리 API")
public class StoryViewUserController {

    private final StoryViewUserService storyViewUserService;

    @Operation(summary = "스토리 조회 유저 조회", description = "해당 스토리를 조회한 유저 정보를 조회")
    @Parameter(name = "storyId", description = "스토리 번호", required = true)
    @GetMapping("/api/stories/view/{storyId}")
    public ResponseEntity<ResultResponseDTO> getViewUserList(@PathVariable("storyId") Long storyId) {

        List<StoryViewUserResponse> views = storyViewUserService.getStoryViewWithStory(storyId);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.STORY_VIEW_USER_SUCCESS, views));
    }
}
