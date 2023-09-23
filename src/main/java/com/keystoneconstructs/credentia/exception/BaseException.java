package com.keystoneconstructs.credentia.exception;

public abstract class BaseException extends Exception {

    private String message;

    public BaseException( String message ) {
        super( message );
    }

    public BaseException( String message, Throwable cause ) {
        super( message, cause );
    }
}
