package com.keystoneconstructs.credentia.controller;

import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
import com.keystoneconstructs.credentia.exception.InvalidInputException;
import com.keystoneconstructs.credentia.model.*;
import com.keystoneconstructs.credentia.service.CertifierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Tag( name = "Certifier Controller", description = "Controller for all Certifier API endpoints." )
@RequestMapping( "/api/v1/certifier" )
public class CertifierController {


    @Autowired
    CertifierService certifierService;


    @Operation( summary = "API to certify Users.",
            description = "This API certifies Users based on Certifier Request." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = CertifierResponse.class ), mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @PutMapping( "/certify" )
    public ResponseEntity<ApiResponse<List<CertifierResponse>>> certifyUsers(
            @RequestBody CertifierRequest certifierRequest ) {

        ApiResponse<List<CertifierResponse>> response = new ApiResponse<>();

        try {

            response.setSuccess( true );
            response.setMessage( "Successfully certified Users." );
            response.setResponse( certifierService.certifyUsers( certifierRequest ) );

            return new ResponseEntity<>( response, HttpStatus.CREATED );

        } catch( InvalidInputException | EntityNotFoundException | AppException e ) {

            response.setSuccess( false );
            response.setMessage( e.getMessage() );
            response.setErrorCode( Arrays.toString( e.getStackTrace() ) );

            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );

        }

    }


    @Operation( summary = "API to get existing Certifier by Id.",
            description = "This API retrieves an existing Certifier based on Certifier Id." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = CertifierResponse.class ), mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @GetMapping( "/getById" )
    public ResponseEntity<ApiResponse<CertifierResponse>> getCertifierById(
            @RequestParam( name = "certifierId" ) String certifierId ) {

        ApiResponse<CertifierResponse> response = new ApiResponse<>();

        try {

            response.setSuccess( true );
            response.setMessage( "Successfully retrieved Certifier." );
            response.setResponse( certifierService.getCertifierById( certifierId ) );

            return new ResponseEntity<>( response, HttpStatus.OK );

        } catch( InvalidInputException | EntityNotFoundException e ) {

            response.setSuccess( false );
            response.setMessage( e.getMessage() );
            response.setErrorCode( Arrays.toString( e.getStackTrace() ) );

            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );

        }

    }


    @Operation( summary = "API to get all existing Certifiers by Recipient Mail.",
            description = "This API retrieves all existing Certifiers based on Recipient Email." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = CertifierResponse.class ), mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @GetMapping( "/getByEmail" )
    public ResponseEntity<ApiResponse<List<CertifierResponse>>> getCertifierByEmail(
            @RequestParam( name = "recipientEmail" ) String recipientEmail ) {

        ApiResponse<List<CertifierResponse>> response = new ApiResponse<>();

        try {

            response.setSuccess( true );
            response.setMessage( "Successfully retrieved Certifiers." );
            response.setResponse( certifierService.getCertifierByEmail( recipientEmail ) );

            return new ResponseEntity<>( response, HttpStatus.OK );

        } catch( InvalidInputException e ) {

            response.setSuccess( false );
            response.setMessage( e.getMessage() );
            response.setErrorCode( Arrays.toString( e.getStackTrace() ) );

            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );

        }

    }


    @Operation( summary = "API to get all existing Certifiers by Certificate Id.",
            description = "This API retrieves all existing Certifiers based on Certificate Id." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = CertifierResponse.class ), mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @GetMapping( "/getByCertificate" )
    public ResponseEntity<ApiResponse<List<CertifierResponse>>> getCertifierByCertificateId(
            @RequestParam( name = "certificateId" ) String certificateId ) {

        ApiResponse<List<CertifierResponse>> response = new ApiResponse<>();

        try {

            response.setSuccess( true );
            response.setMessage( "Successfully retrieved Certifiers." );
            response.setResponse( certifierService.getAllByCertificateId( certificateId ) );

            return new ResponseEntity<>( response, HttpStatus.OK );

        } catch( InvalidInputException | EntityNotFoundException e ) {

            response.setSuccess( false );
            response.setMessage( e.getMessage() );
            response.setErrorCode( Arrays.toString( e.getStackTrace() ) );

            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );

        }

    }

    @Operation( summary = "API to get all existing Certifiers by Organization Name.",
            description = "This API retrieves all existing Certifiers based on Organization Name." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = CertifierResponse.class ), mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @GetMapping( "/getByOrganization" )
    public ResponseEntity<ApiResponse<List<CertifierResponse>>> getCertifierByOrganization(
            @RequestParam( name = "organization" ) String organization ) {

        ApiResponse<List<CertifierResponse>> response = new ApiResponse<>();

        try {

            response.setSuccess( true );
            response.setMessage( "Successfully retrieved Certifiers." );
            response.setResponse( certifierService.getAllByOraganization( organization ) );

            return new ResponseEntity<>( response, HttpStatus.OK );

        } catch( InvalidInputException | EntityNotFoundException e ) {

            response.setSuccess( false );
            response.setMessage( e.getMessage() );
            response.setErrorCode( Arrays.toString( e.getStackTrace() ) );

            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );

        }

    }


    @Operation( summary = "API to delete an Certifier by Id.",
            description = "This API deletes an existing Certifier based on Certifier Id." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = String.class ), mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @PostMapping( "/deleteById" )
    public ResponseEntity<ApiResponse<String>> deleteById( @RequestParam( name = "certifierId" ) String certifierId ) {

        ApiResponse<String> response = new ApiResponse<>();
        try {

            response.setSuccess( true );
            response.setMessage( "Successfully deleted Certificate." );
            response.setResponse( certifierService.deleteCertifierById( certifierId ) );

            return new ResponseEntity<>( response, HttpStatus.OK );

        } catch( InvalidInputException | AppException | EntityNotFoundException e ) {

            response.setSuccess( false );
            response.setMessage( e.getMessage() );
            response.setErrorCode( Arrays.toString( e.getStackTrace() ) );

            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );

        }

    }


}
