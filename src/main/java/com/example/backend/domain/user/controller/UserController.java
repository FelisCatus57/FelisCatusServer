package com.example.backend.domain.user.controller;

import com.example.backend.domain.user.dto.*;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.exception.JwtExpiredTokenException;
import com.example.backend.domain.user.service.UserService;
import com.example.backend.global.authorization.jwt.service.JwtService;
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
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ResponseBody
@RestController
@RequiredArgsConstructor
@Tag(name = "Auth", description = "유저 API")
public class UserController {

    private final AuthUtil authUtil;
    private final JwtService jwtService;
    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원 가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "회원가입 실패 \t\n 1. 이미 존재하는 회원일 경우 \t\n 2. 아이디가 중복 될 경우 \t\n 3. 닉네임이 중복 될 경우", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
    })
    @PostMapping(value = "/signup")
    public ResponseEntity<ResultResponseDTO> signUp(@RequestBody UserRegisterRequest userRegisterRequest) throws Exception {

        User registerUser = userService.signUp(userRegisterRequest);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.REGISTER_SUCCESS, new UserDTO(registerUser)));
    }

    @Operation(summary = "메뉴용 미니 유저 정보", description = "메뉴에서 사용할 간단한 유저 정보")
    @GetMapping("/api/profile")
    public ResponseEntity<ResultResponseDTO> getMenuResponse() {

        MiniMenuUserResponse miniMenuUser = userService.getMiniMenuUser();

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.USER_MENU_VIEW_SUCCESS, miniMenuUser));
    }

    @Operation(summary = "유저 페이지", description = "유저 페이지")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 조회 성공", content = @Content(schema = @Schema(implementation = UserProfileResponse.class))),
            @ApiResponse(responseCode = "400", description = "유저 조회 실패 \t\n 1. 유저가 존재하지 않을 경우", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Parameter(name = "nickname", description = "유저 닉네임", required = true)
    @GetMapping("/api/{nickname}")
    public ResponseEntity<ResultResponseDTO> myPage(@PathVariable("nickname") String nickname) {

        UserProfileResponse profile = userService.getProfile(nickname);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.USER_PAGE_VIEW_SUCCESS, profile));

    }

    @Operation(summary = "유저 상세정보 수정", description = "상제정보 수정 페이지")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 상세정보 수정 성공", content = @Content(schema = @Schema(implementation = UserProfileResponse.class))),
            @ApiResponse(responseCode = "400", description = "유저 상세정보 수정 실패 \t\n 1. 유저가 존재하지 않을 경우", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "유저 상세정보 수정 실패 \t\n 1. 수정 하려는 유저가 본인이 아닐 경우", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/api/accounts/edit")
    public ResponseEntity<ResultResponseDTO> updateProfile(@RequestBody UserProfileEditRequest edit) {

        userService.modifyProfile(edit);
        UserProfileResponse profile = userService.getProfile(authUtil.getLoginUserNickname());

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.USER_PROFILE_UPDATE_SUCCESS, profile));
    }

    @Operation(summary = "유저 이미지 변경", description = "유저 사진 변경")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 이미지 수정 성공"),
            @ApiResponse(responseCode = "400", description = "유저 이미지 수정 실패 \t\n 1. 유저가 존재하지 않을 경우 \t\n 2. 이미지의 파일 타입이 올바르지 않을 경우", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "유저 이미지 수정 실패 \t\n 1. 수정 하려는 유저가 본인이 아닐 경우", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping("/api/accounts/image")
    public ResponseEntity<ResultResponseDTO> updateImage(@Parameter(name = "프로플 이미지", description = "수정할 프로필 이미지", required = true) @RequestPart("image") MultipartFile image) {

        userService.updateImage(image);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.USER_PROFILE_IMAGE_UPDATE_SUCCESS));
    }


    @Operation(summary = "유저 이미지 삭제", description = "유저 사진 삭제/리셋")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 이미지 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "유저 이미지 삭제 실패 \t\n 1. 유저가 존재하지 않을 경우", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "유저 이미지 삭제 실패 \t\n 1. 수정 하려는 유저가 본인이 아닐 경우", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/api/accounts/image")
    public ResponseEntity<ResultResponseDTO> removeImage() {

        userService.deleteImage();

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.USER_PROFILE_IMAGE_REMOVE_SUCCESS));
    }

    @Operation(summary = "토큰 재발급", description = "Refresh 토큰으로 매칭 후 Access 토큰 재발급")
    @PostMapping("/api/reissue")
    public ResponseEntity<ResultResponseDTO> reIssueAccessToken(HttpServletResponse response, String refreshToken) {

        if (!refreshToken.isEmpty() && refreshToken.startsWith("Bearer")){
            String token = refreshToken.split(" ")[1];

            if (jwtService.isTokenValid(token)) {
                jwtService.checkRefreshTokenAndReIssueAccessToken(response, token);
            } else {
                throw new JwtExpiredTokenException();
            }
        }

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.TOKEN_REISSUE_SUCCESS));
    }

    @Operation(summary = "유저 검색", description = "닉네임으로 유저 검색 (닉네임 포함된 문자 검색 가능)")
    @GetMapping("/api/accounts/search")
    public ResponseEntity<ResultResponseDTO> search(@RequestParam("keyword") String keyword) {

        List<SearchUserMiniResponse> findUserList = userService.searchUser(keyword);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.USER_SEARCH_SUCCESS, findUserList));
    }
}
