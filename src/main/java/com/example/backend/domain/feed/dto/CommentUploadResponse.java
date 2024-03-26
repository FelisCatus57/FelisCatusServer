package com.example.backend.domain.feed.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class CommentDTO {

    private Long postId;

    private UserResponseDTO userResponseDTO;


}
