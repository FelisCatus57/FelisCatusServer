package com.example.backend.domain.story.contorller;


import com.example.backend.domain.story.dto.StoryRequest;
import com.example.backend.domain.story.dto.StoryResponse;
import com.example.backend.domain.story.repository.StoryRepository;
import com.example.backend.domain.story.service.StoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StoryController{

    private final StoryService storyService;

    public StoryController(StoryService storyService){
        this.storyService = storyService;
    }
    @PostMapping("/story")
    public ResponseEntity<StoryResponse> uploadStory(@RequestBody StoryRequest storyRequest){
        storyService.uploadStory(storyRequest);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/story/{id}")
    public ResponseEntity<StoryResponse> deleteStory(@PathVariable Long id){
        storyService.deleteStory(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/story")
    public ResponseEntity<StoryResponse> findAll(){
        return ResponseEntity.ok().build();
    }


}