package com.example.backend.domain.feed.controller;

import com.example.backend.domain.feed.dto.*;
import com.example.backend.domain.feed.service.PostLikeService;
import com.example.backend.domain.feed.service.PostService;
import com.example.backend.global.result.ResultCodeMessage;
import com.example.backend.global.result.ResultResponseDTO;
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

import java.util.List;

@ResponseBody
@RestController
@RequiredArgsConstructor
@Tag(name = "Post", description = "게시물 API")
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;

    @Operation(summary = "게시물 작성", description = "[form-data 형식] content: 내용 / files: 이미지 (여러장 가능) [ 이미지 확장자 지원 : \"PNG\" , \"JPG\"]")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "게시글 작성 성공", content = @Content(schema = @Schema(implementation = PostUploadResponse.class)))
    })
    @PostMapping(path = "/api/post")
    public ResponseEntity<ResultResponseDTO> uploadPost(@ModelAttribute PostUploadRequest postUploadRequest) {

        PostUploadResponse postUploadResponse = postService.postUpload(postUploadRequest);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.POST_SUCCESS, postUploadResponse));

    }

    @Operation(summary = "게시물 삭제", description = "게시물 삭제")
    @Parameter(name = "postId", description = "게시물 번호", required = true)
    @DeleteMapping("/api/post/{postId}")
    public ResponseEntity<ResultResponseDTO> deletePost(@PathVariable("postId") Long postId) {

        postService.deletePost(postId);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.POST_DELETE_SUCCESS));
    }

    @Operation(summary = "게시물 수정", description = "게시물 수정 페이지")
    @PutMapping("/api/post/{postId}")
    public ResponseEntity<ResultResponseDTO> editPost(@PathVariable("postId") Long postId, @RequestBody PostEditRequest postEditRequest) {

        PostResponse postResponse = postService.modifyPost(postId, postEditRequest);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.POST_UPDATE_SUCCESS, postResponse));
    }

    @Operation(summary = "게시물 좋아요", description = "게시물 좋아요")
    @Parameter(name = "postId", description = "게시물 번호", required = true)
    @PostMapping("/api/post/{postId}/like")
    public ResponseEntity<ResultResponseDTO> postLike(@PathVariable("postId") Long postId) {

        postLikeService.postLike(postId);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.POST_LIKE_SUCCESS));

    }

    @Operation(summary = "게시물 좋아요 취소", description = "게시물 좋아요 취소")
    @Parameter(name = "postId", description = "게시물 번호", required = true)
    @DeleteMapping("/api/post/{postId}/unlike")
    public ResponseEntity<ResultResponseDTO> postUnLike(@PathVariable("postId") Long postId) {

        postLikeService.postUnlike(postId);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.POST_UNLIKE_SUCCESS));
    }

    @Operation(summary = "게시물 좋아요 유저 목록", description = "게시물 좋아요 유저 목록")
    @Parameter(name = "postId", description = "게시물 번호", required = true)
    @GetMapping("/api/post/{postId}/like")
    public ResponseEntity<ResultResponseDTO> getPostLikeUser(@PathVariable("postId") Long postId) {

        List<PostLikeUserResponse> postLikeUser = postService.getPostLikeUser(postId);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.GET_POST_LIKE_USER_SUCCESS,postLikeUser));
    }

    @Operation(summary = "내가 팔로우 한 유저 포스트", description = "내가 팔로우한 유저 포스트만 가져오기")
    @GetMapping("/api/followingPost")
    public ResponseEntity<ResultResponseDTO> postView() {

        List<PostResponse> userFollowingPost = postService.getUserFollowingPost();

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.GET_FOLLOW_USER_POST_SUCCESS, userFollowingPost));
    }

    @Operation(summary = "내가 팔로우 하지 않은 유저 포스트", description = "내가 팔로우 하지 않은 유저 포스트만 가져오기")
    @GetMapping("/api/explorePost")
    public ResponseEntity<ResultResponseDTO> exploreView() {

        List<PostResponse> userNotFollowingPost = postService.getUserNotFollowingPost();

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.GET_NOT_FOLLOW_USER_POST_SUCCESS, userNotFollowingPost));
    }

//    @Operation(summary = "게시물 가져오기", description = "해당 유저가 작성한 게시물 모두 가져오기")
//    @GetMapping("/api/post/{nickname}")
//    public ResponseEntity<ResultResponseDTO> getAllPost(@PathVariable("nickname") String nickname) {
//
//        List<PostResponse> allPostByUserNickname = postService.getAllPostByUserNickname(nickname);
//
//        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.USER_POST_VIEW_SUCCESS, allPostByUserNickname));
//
//    }
}
