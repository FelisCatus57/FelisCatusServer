package com.example.backend.domain.feed.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class CommentNotExistedException extends CustomException {

    public CommentNotExistedException() {
        super(ErrorCodeMessage.COMMENT_NOT_FOUND);
    }
}
