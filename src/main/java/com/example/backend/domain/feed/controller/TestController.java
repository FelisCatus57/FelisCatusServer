package com.example.backend.domain.feed.controller;


import com.example.backend.domain.feed.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ResponseBody
@RequiredArgsConstructor
public class TestController {

    private final PostService postService;

//    @GetMapping("/api/post/{nickname}")
//    public ResponseEntity<ResultResponseDTO> getAllPost(@PathVariable("nickname") String nickname) {
//
//        List<PostResponse> allPostByUserNickname = postService.getAllPostByUserNickname(nickname);
//
//        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.USER_POST_VIEW_SUCCESS, allPostByUserNickname));
//
//    }

//    @DeleteMapping("/api/post/{postId}/unlike")
//    public ResponseEntity<ResultResponseDTO> postUnLike(@PathVariable("postId") Long postId) {
//
//        postLikeService.postUnlike(postId);
//
//        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.POST_UNLIKE_SUCCESS, "좋아요 취소 성공"));
//    }
//
//    @PostMapping("/api/comment/{commentId}/like")
//    public ResponseEntity<ResultResponseDTO> commentLike(@PathVariable("commentId") Long commentId) {
//
//        commentLikeService.commentLike(commentId);
//
//        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.COMMENT_LIKE_SUCCESS, "댓글 좋아요 성공"));
//    }
//
//    @DeleteMapping("/api/comment/{commentId}/unlike")
//    public ResponseEntity<ResultResponseDTO> commentUnLike(@PathVariable("commentId") Long commentId) {
//
//        commentLikeService.commentUnlike(commentId);
//
//        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.COMMENT_UNLIKE_SUCCESS, "댓글 좋아요 취소 성공"));
//    }
}
