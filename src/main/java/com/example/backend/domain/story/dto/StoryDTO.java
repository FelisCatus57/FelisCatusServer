package com.example.backend.domain.story.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@NoArgsConstructor
@AllArgsConstructor
public class StoryDTO {

    @GetMapping("/story")
    public String getStory(){
        return "story";
    }

}
