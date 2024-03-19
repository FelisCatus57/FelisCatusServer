package com.example.backend.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class UserProfileEditDTO {
    private Long id;

    private String username;

    private String name;

    private String nickname;

//    private String
}
