package com.example.backend.domain.user.dto;

import com.example.backend.domain.user.entity.User;
import com.example.backend.global.image.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String name;

    private String nickname;

    private Image image;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.image = user.getImage();
    }
}
