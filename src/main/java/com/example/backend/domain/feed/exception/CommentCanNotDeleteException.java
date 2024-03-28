package com.example.backend.domain.feed.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class CommentCanNotDeleteException extends CustomException {
    public CommentCanNotDeleteException() {
        super(ErrorCodeMessage.COMMENT_USER_DELETE_FAIL);
    }
}
