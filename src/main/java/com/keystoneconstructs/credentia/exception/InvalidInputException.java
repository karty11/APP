package com.keystoneconstructs.credentia.exception;

public class InvalidInputException extends BaseException {

    public InvalidInputException( ErrorCodeAndMessage errorCodeAndMessage ) {
        super( errorCodeAndMessage );
    }

    public InvalidInputException( ErrorCodeAndMessage errorCodeAndMessage, Throwable cause ) {
        super( errorCodeAndMessage, cause );
    }

}
