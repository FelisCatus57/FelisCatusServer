package com.example.backend.domain.follow.controller;

import com.example.backend.domain.follow.dto.FollowResponse;
import com.example.backend.domain.follow.dto.FollowerResponse;
import com.example.backend.domain.follow.dto.FollowingResponse;
import com.example.backend.domain.follow.service.FollowService;
import com.example.backend.global.result.ResultCodeMessage;
import com.example.backend.global.result.ResultResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Follow", description = "팔로우 API")
public class FollowController {
    private final FollowService followService;

    @Operation(summary = "유저 팔로우", description = "해당 닉네임 유저 팔로우")
    @Parameter(name = "nickname", description = "유저 닉네임", required = true)
    @PostMapping("/api/{nickname}/follow")
    public ResponseEntity<ResultResponseDTO> follow(@PathVariable String nickname) {
        FollowResponse res = followService.follow(nickname);
        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.FOLLOW_SUCCESS, res));
    }

    @Operation(summary = "유저 언팔로우", description = "해당 닉네임 유저 언팔로우")
    @Parameter(name = "nickname", description = "유저 닉네임", required = true)
    @PostMapping("/api/{nickname}/unfollow")
    public ResponseEntity<ResultResponseDTO> unfollow(@PathVariable String nickname) {
        followService.unfollow(nickname);
        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.UNFOLLOW_SUCCESS));
    }

    @Operation(summary = "팔로워 가져오기", description = "해당 닉네임 유저 팔로워 가져오기")
    @Parameter(name = "nickname", description = "유저 닉네임", required = true)
    @GetMapping("/api/{nickname}/followers")
    public ResponseEntity<ResultResponseDTO> followerList(@PathVariable String nickname) {

        List<FollowerResponse> followerResponses = followService.followerList(nickname);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.FOLLOWER_VIEW_SUCCESS, followerResponses ));
    }

    @Operation(summary = "팔로잉 가져오기", description = "해당 닉네임 유저 팔로잉 가져오기")
    @Parameter(name = "nickname", description = "유저 닉네임", required = true)
    @GetMapping("/api/{nickname}/following")
    public ResponseEntity<ResultResponseDTO> followingList(@PathVariable String nickname) {

        List<FollowingResponse> followingResponses = followService.followingList(nickname);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.FOLLOWING_VIEW_SUCCESS, followingResponses  ));
    }
}
