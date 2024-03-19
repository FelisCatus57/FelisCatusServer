package com.example.backend.global.error.exception;


import com.example.backend.global.error.ErrorCodeMessage;

public class EntityNotExistedException extends CustomException {

    public EntityNotExistedException(ErrorCodeMessage errorCodeMessage) {
        super(errorCodeMessage);
    }
}
