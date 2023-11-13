package com.keystoneconstructs.credentia.exception;

public abstract class BaseException extends Exception {

    private ErrorCodeAndMessage errorCodeAndMessage;


    protected BaseException( ErrorCodeAndMessage errorCodeAndMessage ) {

        super( errorCodeAndMessage.getMessage() );
        this.errorCodeAndMessage = errorCodeAndMessage;
    }


    protected BaseException( ErrorCodeAndMessage errorCodeAndMessage, Throwable cause ) {

        super( errorCodeAndMessage.getMessage(), cause );
        this.errorCodeAndMessage = errorCodeAndMessage;
    }


    public String getErrorCode() {

        return this.errorCodeAndMessage.getErrorCode();

    }


    public String getErrorMessage() {

        return this.errorCodeAndMessage.getMessage();

    }

}
