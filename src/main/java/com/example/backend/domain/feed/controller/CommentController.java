package com.example.backend.domain.feed.controller;

import com.example.backend.domain.feed.dto.CommentUploadRequest;
import com.example.backend.domain.feed.dto.CommentResponse;
import com.example.backend.domain.feed.service.CommentLikeService;
import com.example.backend.domain.feed.service.CommentService;
import com.example.backend.global.result.ResultCodeMessage;
import com.example.backend.global.result.ResultResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ResponseBody
@RestController
@RequiredArgsConstructor
@Tag(name = "Comment", description = "댓글 API")
public class CommentController {

    private final CommentService commentService;
    private final CommentLikeService commentLikeService;

    @Operation(summary = "댓글 가져오기", description = "포스트에 달린 댓글 모두 가져오기")
    @Parameter(name = "postId", description = "게시글 번호", required = true)
    @GetMapping("/api/{postId}/comment")
    public ResponseEntity<ResultResponseDTO> getComments(@PathVariable("postId") Long postId) {

        List<CommentResponse> allCommentByPostId = commentService.getAllParentCommentByPostId(postId);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.POST_COMMENT_VIEW_SUCCESS, allCommentByPostId));
    }

    @Operation(summary = "부모 댓글 작성", description = "댓글 작성")
    @Parameter(name = "postId", description = "게시글 번호", required = true)
    @ApiResponse(responseCode = "200", description = "댓글 작성 성공", content = @Content(schema = @Schema(implementation = CommentResponse.class)))
    @PostMapping("/api/{postId}/comment")
    public ResponseEntity<ResultResponseDTO> uploadComment(@PathVariable("postId") Long postId, @RequestBody CommentUploadRequest commentUploadRequest) {

        CommentResponse commentResponse = commentService.commentUpload(commentUploadRequest, postId);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.COMMENT_SUCCESS, commentResponse));
    }

    @Operation(summary = "자식 댓글 작성", description = "댓글 작성")
    @Parameter(name = "postId", description = "게시글 번호", required = true)
    @Parameter(name = "commentId", description = "부모 댓글 번호", required = true)
    @ApiResponse(responseCode = "200", description = "댓글 작성 성공", content = @Content(schema = @Schema(implementation = CommentResponse.class)))
    @PostMapping("/api/{postId}/comment/{commentId}")
    public ResponseEntity<ResultResponseDTO> uploadChildrenComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId , @RequestBody CommentUploadRequest commentUploadRequest) {

        CommentResponse commentResponse = commentService.childrenCommentUpload(commentUploadRequest, postId, commentId);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.COMMENT_SUCCESS, commentResponse));
    }

    @Operation(summary = "댓글 삭제", description = "댓글 삭제 [부모 댓글이 삭제될 경우 자식 댓글은 모두 삭제]")
    @Parameter(name = "commentId", description = "댓글 번호", required = true)
    @DeleteMapping("/api/{postId}/comment/{commentId}")
    public ResponseEntity<ResultResponseDTO> deleteComment(@PathVariable("commentId") Long commentId) {

        commentService.deleteComment(commentId);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.COMMENT_DELETE_SUCCESS));
    }

    @Operation(summary = "댓글 좋아요", description = "댓글 좋아요")
    @Parameter(name = "commentId", description = "댓글 번호", required = true)
    @PostMapping("/api/comment/{commentId}/like")
    public ResponseEntity<ResultResponseDTO> commentLike(@PathVariable("commentId") Long commentId) {

        commentLikeService.commentLike(commentId);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.COMMENT_LIKE_SUCCESS));
    }

    @Operation(summary = "댓글 좋아요 취소", description = "댓글 좋아요 취소")
    @Parameter(name = "commentId", description = "댓글 번호", required = true)
    @DeleteMapping("/api/comment/{commentId}/unlike")
    public ResponseEntity<ResultResponseDTO> commentUnLike(@PathVariable("commentId") Long commentId) {

        commentLikeService.commentUnlike(commentId);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.COMMENT_UNLIKE_SUCCESS));
    }
}
