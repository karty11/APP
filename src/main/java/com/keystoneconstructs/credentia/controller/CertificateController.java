package com.keystoneconstructs.credentia.controller;

import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
import com.keystoneconstructs.credentia.exception.InvalidInputException;
import com.keystoneconstructs.credentia.model.ApiResponse;
import com.keystoneconstructs.credentia.model.CertificateRequest;
import com.keystoneconstructs.credentia.model.CertificateResponse;
import com.keystoneconstructs.credentia.service.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement( name = "credentia_auth" )
@Tag( name = "Certificate Controller", description = "Controller for all Certificate API endpoints." )
@RequestMapping( "/api/v1/certificate" )
public class CertificateController {


    @Autowired
    CertificateService certificateService;


    @Operation( summary = "API to create new Certificate.",
            description = "This API creates a new Certificate based on Certificate Request." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",
            content = { @Content( schema = @Schema( implementation = CertificateResponse.class ),
                    mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @PutMapping( "/add" )
    public ResponseEntity<ApiResponse<CertificateResponse>> createCertificate(
            @RequestBody CertificateRequest certificateRequest, @RequestHeader( "userId" ) String userId )
            throws InvalidInputException, AppException, EntityNotFoundException {

        ApiResponse<CertificateResponse> response = new ApiResponse<>();
        response.setSuccess( true );
        response.setMessage( "Successfully created new Certificate." );
        response.setResponse( certificateService.addCertificate( certificateRequest, userId ) );

        return new ResponseEntity<>( response, HttpStatus.CREATED );

    }


    @Operation( summary = "API to update existing Certificate.",
            description = "This API updates an existing Certificate based on Certificate Id and Certificate Request." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",
            content = { @Content( schema = @Schema( implementation = CertificateResponse.class ),
                    mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @PostMapping( "/edit" )
    public ResponseEntity<ApiResponse<CertificateResponse>> updateCertificate(
            @RequestParam( name = "certificateId" ) String certificateId,
            @RequestBody CertificateRequest certificateRequest,
            @RequestHeader( "userId" ) String userId )
            throws InvalidInputException, AppException, EntityNotFoundException {

        ApiResponse<CertificateResponse> response = new ApiResponse<>();
        response.setSuccess( true );
        response.setMessage( "Successfully updated Certificate." );
        response.setResponse( certificateService.updateCertificate( certificateId, certificateRequest, userId ) );

        return new ResponseEntity<>( response, HttpStatus.OK );

    }


    @Operation( summary = "API to get existing Certificate by Id.",
            description = "This API retrieves an existing Certificate based on Certificate Id." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",
            content = { @Content( schema = @Schema( implementation = CertificateResponse.class ),
                    mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @GetMapping( "/getById" )
    public ResponseEntity<ApiResponse<CertificateResponse>> getCertificateById(
            @RequestParam( name = "certificateId" ) String certificateId )
            throws InvalidInputException, EntityNotFoundException {

        ApiResponse<CertificateResponse> response = new ApiResponse<>();
        response.setSuccess( true );
        response.setMessage( "Successfully retrieved Certificate." );
        response.setResponse( certificateService.findCertificateById( certificateId ) );

        return new ResponseEntity<>( response, HttpStatus.OK );

    }


    @Operation( summary = "API to get all existing Certificates.",
            description = "This API retrieves all existing Certificates based on Organization Id." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",
            content = { @Content( schema = @Schema( implementation = CertificateResponse.class ),
                    mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @GetMapping( "/getAll" )
    public ResponseEntity<ApiResponse<List<CertificateResponse>>> getAllCertificatesByOrganization(
            @RequestParam( name = "orgId" ) String orgId ) throws InvalidInputException {

        ApiResponse<List<CertificateResponse>> response = new ApiResponse<>();
        response.setSuccess( true );
        response.setMessage( "Successfully retrieved all Certificates." );
        response.setResponse( certificateService.findAllCertificatesByOrganization( orgId ) );

        return new ResponseEntity<>( response, HttpStatus.OK );


    }


    @Operation( summary = "API to delete an Certificate by Id.",
            description = "This API deletes an existing Certificate based on Certificate Id." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",
            content = { @Content( schema = @Schema( implementation = String.class ),
                    mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @PostMapping( "/deleteById" )
    public ResponseEntity<ApiResponse<String>> deleteById(
            @RequestParam( name = "certificateId" ) String certificateId )
            throws InvalidInputException, AppException, EntityNotFoundException {

        ApiResponse<String> response = new ApiResponse<>();
        response.setSuccess( true );
        response.setMessage( "Successfully deleted Certificate." );
        response.setResponse( certificateService.deleteCertificateById( certificateId ) );

        return new ResponseEntity<>( response, HttpStatus.OK );


    }


}
