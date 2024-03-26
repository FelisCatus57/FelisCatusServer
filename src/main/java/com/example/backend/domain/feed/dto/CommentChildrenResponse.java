package com.example.backend.domain.feed.dto;

import com.example.backend.domain.feed.entity.Comment;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentChildrenResponse {

    private Long commentId;

    private String content;

    private String createdDate;

    private UserResponseDTO userResponseDTO;

    public List<CommentChildrenResponse> CommentParentResponse(List<Comment> children) {

        List<CommentChildrenResponse> responses = new ArrayList<>();

        children.forEach( ch -> {

            CommentChildrenResponse CommentChildrenResponse = new CommentChildrenResponse(ch.getId(), ch.getContent(), ch.getCreatedDate(), new UserResponseDTO(ch.getUser()));

            responses.add(CommentChildrenResponse);

        });

        return responses;
    }

}
