package com.example.backend.global.error.exception;

import com.example.backend.global.error.ErrorCodeMessage;

public class FileConvertException extends CustomException {

    public FileConvertException() {
        super(ErrorCodeMessage.FILE_CONVERT_FAIL);
    }
}
