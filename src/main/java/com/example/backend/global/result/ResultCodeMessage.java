package com.example.backend.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCodeMessage {

    // User
    REGISTER_SUCCESS(200, "회원가입에 성공하였습니다."),
    LOGIN_SUCCESS(200, "로그인에 성공하였습니다."),
    REISSUE_SUCCESS(200, "토큰 재발급에 성공하였습니다."),

    POST_SUCCESS(200, "게시물 작성에 성공하였습니다."),
    POST_DELETE_SUCCESS(200, "게시물 삭제에 성공하였습니다."),
    GET_USER_POST_SUCCESS(200, "유저 게시물 조회에 성공하였습니다."),

    COMMENT_SUCCESS(200, "댓글 작성에 성공하였습니다."),
    COMMENT_DELETE_SUCCESS(200, "댓글 삭제에 성공하였습니다."),

    POST_LIKE_SUCCESS(200, "게시물 좋아요에 성공하였습니다."),
    POST_UNLIKE_SUCCESS(200, "게시물 좋아요 취소에 성공하였습니다.");



    private final int status;
    private final String message;


}
