package com.example.backend.domain.user.dto;

import lombok.Data;

@Data
public class UserRegisterRequest {

    private String username; // 아이디
    
    private String password; // 비밀번호
    
    private String name; // 이름
    
    private String nickname; // 닉네임
}
