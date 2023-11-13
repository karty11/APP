package com.keystoneconstructs.credentia.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ApiResponse<T> {

    private T response;

    private boolean success = true;

    private String message;

    private String errorCode;


    public ApiResponse() {

    }


    public ApiResponse( T response, boolean success, String message, String errorCode ) {

        this.response = response;
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
    }


    public T getResponse() {

        return response;
    }


    public void setResponse( T response ) {

        this.response = response;
    }


    public boolean isSuccess() {

        return success;
    }


    public void setSuccess( boolean success ) {

        this.success = success;
    }


    public String getMessage() {

        return message;
    }


    public void setMessage( String message ) {

        this.message = message;
    }


    public String getErrorCode() {

        return errorCode;
    }


    public void setErrorCode( String errorCode ) {

        this.errorCode = errorCode;
    }


    @Override
    public boolean equals( Object o ) {

        if( this == o ) {
            return true;
        }

        if( o == null || getClass() != o.getClass() ) {
            return false;
        }

        ApiResponse<?> that = (ApiResponse<?>) o;

        return new EqualsBuilder().append( success, that.success ).append( response, that.response )
                .append( message, that.message ).append( errorCode, that.errorCode ).isEquals();
    }


    @Override
    public int hashCode() {

        return new HashCodeBuilder( 17, 37 ).append( response ).append( success ).append( message ).append( errorCode )
                .toHashCode();
    }

}
