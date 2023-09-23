package com.keystoneconstructs.credentia.controller;

import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
import com.keystoneconstructs.credentia.exception.InvalidInputException;
import com.keystoneconstructs.credentia.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class CredentiaControllerAdvice {

    @ExceptionHandler( Exception.class )
    public ResponseEntity<ApiResponse<String>> handleExceptions( Exception e ) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        String stackTrace = convertStackTraceToString( e );

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess( false );
        apiResponse.setMessage( e.getMessage() );
        apiResponse.setErrorCode( stackTrace );
        return new ResponseEntity<>( apiResponse, status );
    }

    @ExceptionHandler( EntityNotFoundException.class )
    public ResponseEntity<ApiResponse<String>> handleEntityNotFoundExceptions( Exception e ) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        String stackTrace = convertStackTraceToString( e );

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess( false );
        apiResponse.setMessage( e.getMessage() );
        apiResponse.setErrorCode( stackTrace );
        return new ResponseEntity<>( apiResponse, status );
    }

    @ExceptionHandler( InvalidInputException.class )
    public ResponseEntity<ApiResponse<String>> handleInvalidInputExceptions( Exception e ) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        String stackTrace = convertStackTraceToString( e );

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess( false );
        apiResponse.setMessage( e.getMessage() );
        apiResponse.setErrorCode( stackTrace );
        return new ResponseEntity<>( apiResponse, status );
    }

    @ExceptionHandler( AppException.class )
    public ResponseEntity<ApiResponse<String>> handleAppExceptions( Exception e ) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        String stackTrace = convertStackTraceToString( e );

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess( false );
        apiResponse.setMessage( e.getMessage() );
        apiResponse.setErrorCode( stackTrace );
        return new ResponseEntity<>( apiResponse, status );
    }

    @ExceptionHandler( NullPointerException.class )
    public ResponseEntity<ApiResponse<String>> handleNullPointerExceptions( NullPointerException e ) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        String stackTrace = convertStackTraceToString( e );

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess( false );
        apiResponse.setMessage( e.getMessage() );
        apiResponse.setErrorCode( stackTrace );
        return new ResponseEntity<>( apiResponse, status );
    }


    @ExceptionHandler( RuntimeException.class )
    public ResponseEntity<ApiResponse<String>> handleRuntimeExceptions( RuntimeException e ) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        String stackTrace = convertStackTraceToString( e );

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess( false );
        apiResponse.setMessage( e.getMessage() );
        apiResponse.setErrorCode( stackTrace );
        return new ResponseEntity<>( apiResponse, status );
    }

    private String convertStackTraceToString( Exception e ) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter( stringWriter );
        e.printStackTrace( printWriter );
        return stringWriter.toString();
    }


}
