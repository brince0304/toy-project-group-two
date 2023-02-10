package com.toyboardproject.exception;


public class NotAuthorizedException extends IllegalAccessException{
    public NotAuthorizedException() {
        super("비정상적인 접근입니다!");
    }

    public NotAuthorizedException(String message){
        super(message);
    }
}