package com.board.board.global.error;

public class NotMyEntityException extends InvalidValueException{
    public NotMyEntityException(String value) {
        super(value, ErrorCode.NOT_MY_ENTITY);
    }
}
