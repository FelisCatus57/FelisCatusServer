package com.example.backend.domain.user.controller;

import com.example.backend.domain.user.dto.UserRegisterRequest;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.domain.user.service.UserService;
import com.example.backend.global.result.ResultCodeMessage;
import com.example.backend.global.result.ResultResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/sign-up")
    @ResponseBody
    public ResponseEntity<ResultResponseDTO> signUp(@RequestBody UserRegisterRequest userRegisterRequest) throws Exception {
        userService.signUp(userRegisterRequest);
        User user = userRepository.findByUsername(userRegisterRequest.getUsername()).get();
        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.REGISTER_SUCCESS, user));
    }

}
