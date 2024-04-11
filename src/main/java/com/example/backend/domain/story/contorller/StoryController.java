package com.example.backend.domain.story.contorller;


import com.example.backend.domain.story.dto.StoryRequest;
import com.example.backend.domain.story.dto.StoryResponse;
import com.example.backend.domain.story.entity.Story;
import com.example.backend.domain.story.repository.StoryRepository;
import com.example.backend.domain.story.service.StoryService;
import com.example.backend.global.result.ResultCodeMessage;
import com.example.backend.global.result.ResultResponseDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @PostMapping("/story")
    public ResponseEntity<ResultResponseDTO> uploadStory(@RequestBody StoryRequest storyRequest) {
        storyService.uploadStory(storyRequest);
        Story story = storyService.findById(storyRequest.getId());
        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.COMMENT_SUCCES, story));
    }

    @DeleteMapping("/story/{id}")
    public ResponseEntity<ResultResponseDTO> deleteStory(@PathVariable StoryRequest storyRequest) {
        storyService.deleteStory(storyRequest.getId());
        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.COMMENT_SUCCES));
//    }
//    @GetMapping("/story")
//    public ResponseEntity<ResultResponseDTO> findAll(){
//        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.COMMENT_SUCCES));
//
//    }


    }
}