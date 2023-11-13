package com.keystoneconstructs.credentia.exception;

public class AppException extends BaseException {

    public AppException( ErrorCodeAndMessage errorCodeAndMessage ) {

        super( errorCodeAndMessage );
    }


    public AppException( ErrorCodeAndMessage errorCodeAndMessage, Throwable cause ) {

        super( errorCodeAndMessage, cause );
    }

}
