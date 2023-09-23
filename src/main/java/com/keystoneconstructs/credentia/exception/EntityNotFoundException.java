package com.keystoneconstructs.credentia.exception;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException( String message ) {
        super( message );
    }

    public EntityNotFoundException( String message, Throwable cause ) {
        super( message, cause );
    }

}
