package com.keystoneconstructs.credentia.exception;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException( ErrorCodeAndMessage errorCodeAndMessage ) {
        super( errorCodeAndMessage );
    }

    public EntityNotFoundException( ErrorCodeAndMessage errorCodeAndMessage, Throwable cause ) {
        super( errorCodeAndMessage, cause );
    }

}
