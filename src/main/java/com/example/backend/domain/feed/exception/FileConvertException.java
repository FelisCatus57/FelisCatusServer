package com.example.backend.domain.feed.exception;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.CustomException;

public class FileConvertException extends CustomException {

    public FileConvertException() {
        super(ErrorCodeMessage.FILE_CONVERT_FAIL);
    }
}
