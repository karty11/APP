package com.keystoneconstructs.credentia.exception;

public class AppException extends BaseException{

    public AppException( String message ) {
        super( message );
    }

    public AppException( String message, Throwable cause ) {
        super( message, cause );
    }
}
