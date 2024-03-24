package com.example.backend.domain.feed.exception;

import com.example.backend.domain.feed.dto.CommentUploadRequest;
import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class FileAlreadyExistedException extends CustomException {
    public FileAlreadyExistedException() {
        super(ErrorCodeMessage.FILE_ALREADY_EXISTED);
    }
}
