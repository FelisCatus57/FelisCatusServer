package com.example.backend.domain.user.controller;

import com.example.backend.domain.feed.service.PostService;
import com.example.backend.domain.user.dto.UserDTO;
import com.example.backend.domain.user.dto.UserProfileResponse;
import com.example.backend.domain.user.dto.UserRegisterRequest;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.service.UserService;
import com.example.backend.global.error.ErrorResponseDTO;
import com.example.backend.global.result.ResultCodeMessage;
import com.example.backend.global.result.ResultResponseDTO;
import com.example.backend.global.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RestController
@RequiredArgsConstructor
@Tag(name = "Auth", description = "유저 API")
public class UserController {

    private final AuthUtil authUtil;
    private final UserService userService;
    private final PostService postService;


    @Operation(summary = "회원가입", description = "회원 가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "1. 이미 존재하는 회원일 경우 \t\n 2. 아이디가 중복 될 경우 \t\n 3. 닉네임이 중복 될 경우", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
    })
    @PostMapping("/signup")
    public ResponseEntity<ResultResponseDTO> signUp(@RequestBody UserRegisterRequest userRegisterRequest) throws Exception {

        User registerUser = userService.signUp(userRegisterRequest);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.REGISTER_SUCCESS, new UserDTO(registerUser)));
    }

    @Operation(summary = "유저 페이지", description = "유저 페이지")
    @Parameter(name = "nickname", description = "유저 닉네임", required = true)
    @GetMapping("/api/{nickname}")
    public ResponseEntity<ResultResponseDTO> myPage(@PathVariable("nickname") String nickname) {

        UserProfileResponse profile = userService.getProfile(nickname);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.MY_PAGE_VIEW_SUCCESS, profile));

    }

}
