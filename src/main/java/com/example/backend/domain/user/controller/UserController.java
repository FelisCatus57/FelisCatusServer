package com.example.backend.domain.user.controller;

import com.example.backend.domain.user.dto.UserRegisterRequest;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.domain.user.service.UserService;
import com.example.backend.global.result.ResultCodeMessage;
import com.example.backend.global.result.ResultResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Auth", description = "유저 API")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Operation(summary = "회원가입", description = "필요 파라미터 : 아이디, 비밀번호, 이름, 닉네임, 이메일")
    @Parameter(name = "username", description = "유저의 아이디")
    @Parameter(name = "password", description = "유저의 비밀번호")
    @Parameter(name = "name", description = "유저의 이름")
    @Parameter(name = "nickname", description = "유저의 닉네임")
    @Parameter(name = "email", description = "유저의 이메일")
    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<ResultResponseDTO> signUp(@RequestBody UserRegisterRequest userRegisterRequest) throws Exception {
        userService.signUp(userRegisterRequest);
        User user = userRepository.findByUsername(userRegisterRequest.getUsername()).get();
        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.REGISTER_SUCCESS, user));
    }

}
