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
    USER_PROFILE_UPDATE_SUCCESS(200, "유저 정보 수정에 성공하였습니다."),
    USER_PAGE_VIEW_SUCCESS(200, "유저 페이지 조회에 성공하였습니다."),
    USER_PROFILE_IMAGE_UPDATE_SUCCESS(200, "유저 프로필 이미지 변경에 성공하였습니다."),
    USER_PROFILE_IMAGE_REMOVE_SUCCESS(200, "유저 프로필 이미지 삭제에 성공하였습니다."),

    POST_LIKE_SUCCESS(200, "게시물 좋아요에 성공하였습니다."),
    POST_UNLIKE_SUCCESS(200, "게시물 좋아요 취소에 성공하였습니다."),

    USER_POST_VIEW_SUCCESS(200, "유저 게시물 조회에 성공하였습니다."),
    USER_MENU_VIEW_SUCCESS(200, "유저 로그인 응답 조회에 성공하였습니다."),

    POST_SUCCESS(200, "게시물 작성에 성공하였습니다."),
    GET_USER_POST_SUCCESS(200, "유저 게시물 조회에 성공하였습니다."),
    POST_UPDATE_SUCCESS(200, "게시물 수정에 성공하였습니다."),
    POST_DELETE_SUCCESS(200, "게시물 삭제에 성공하였습니다."),

    POST_COMMENT_VIEW_SUCCESS(200, "게시물에 달린 모든 댓글 조회에 성공하였습니다."),
    COMMENT_SUCCESS(200, "댓글 작성에 성공하였습니다."),
    COMMENT_DELETE_SUCCESS(200, "댓글 삭제에 성공하였습니다."),


    COMMENT_LIKE_SUCCESS(200, "댓글 좋아요에 성공하였습니다."),
    COMMENT_UNLIKE_SUCCESS(200, "댓글 좋아요 취소에 성공하였습니다."),

    ACCESS_TOKEN_UPDATE_SUCCESS(200, "액세스 토큰이 재발급 되었습니다.");
//    ACCESS_TOKEN_NOT_EXPIRED(200, "액세스 토큰이 만료되지 않았습니다.");

    private final int status;
    private final String message;


}
