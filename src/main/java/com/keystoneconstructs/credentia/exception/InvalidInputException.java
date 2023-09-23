package com.keystoneconstructs.credentia.exception;

public class InvalidInputException extends BaseException{

    public InvalidInputException( String message ) {
        super( message );
    }

    public InvalidInputException( String message, Throwable cause ) {
        super( message, cause );
    }

}
