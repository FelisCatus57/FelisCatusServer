package com.example.backend.domain.feed.dto;

import com.example.backend.domain.feed.entity.Post;
import com.example.backend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private User user;

    private String content;

    public Post toEntity() {
        return Post.builder()
                .user(user)
                .content(content)
                .build();
    }
}
