package com.keystoneconstructs.credentia.controller;

import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
import com.keystoneconstructs.credentia.exception.InvalidInputException;
import com.keystoneconstructs.credentia.model.*;
import com.keystoneconstructs.credentia.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
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
@Tag( name = "Organization Controller", description = "Controller for all Organization API endpoints." )
@RequestMapping( "/api/v1/organization" )
public class OrganizationController {


    @Autowired
    OrganizationService organizationService;


    @Operation( summary = "API to create new Organization.",
            description = "This API creates a new Organization based on Organization Request." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = OrganizationResponse.class ),
                    mediaType = "application/json" ) } )
    @PutMapping( "/add" )
    public ResponseEntity<ApiResponse<OrganizationResponse>> createOrganization(
            @RequestBody OrganizationRequest organizationRequest ) {

        ApiResponse<OrganizationResponse> response = new ApiResponse<>();
        try {

            response.setSuccess( true );
            response.setMessage( "Successfully created new Organization." );
            response.setResponse( organizationService.createOrgainzation( organizationRequest ) );

            return new ResponseEntity<>( response, HttpStatus.CREATED );

        } catch( InvalidInputException | AppException e ) {

            response.setSuccess( false );
            response.setMessage( e.getMessage() );
            response.setErrorCode( Arrays.toString( e.getStackTrace() ) );

            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );

        }

    }


    @Operation( summary = "API to update existing Organization.",
            description = "This API updates an existing Organization based on Organization Id and Organization Request." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = OrganizationResponse.class ),
                    mediaType = "application/json" ) } )
    @PostMapping( "/edit" )
    public ResponseEntity<ApiResponse<OrganizationResponse>> updateOrganization(
            @RequestParam( name = "orgId" ) String organizationId,
            @RequestBody OrganizationRequest organizationRequest ) {

        ApiResponse<OrganizationResponse> response = new ApiResponse<>();
        try {

            response.setSuccess( true );
            response.setMessage( "Successfully updated Organization." );
            response.setResponse( organizationService.updateOrganization( organizationRequest, organizationId ) );

            return new ResponseEntity<>( response, HttpStatus.OK );

        } catch( InvalidInputException | AppException | EntityNotFoundException e ) {

            response.setSuccess( false );
            response.setMessage( e.getMessage() );
            response.setErrorCode( Arrays.toString( e.getStackTrace() ) );

            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );

        }

    }


    @Operation( summary = "API to get existing Organization by Id.",
            description = "This API retrieves an existing Organization based on Organization Id." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = OrganizationResponse.class ),
                    mediaType = "application/json" ) } )
    @GetMapping( "/getById" )
    public ResponseEntity<ApiResponse<OrganizationResponse>> getOrganizationById(
            @RequestParam( name = "orgId" ) String orgId ) {

        ApiResponse<OrganizationResponse> response = new ApiResponse<>();
        try {

            response.setSuccess( true );
            response.setMessage( "Successfully retrieved Organization." );
            response.setResponse( organizationService.findOrganizationById( orgId ) );

            return new ResponseEntity<>( response, HttpStatus.OK );

        } catch( InvalidInputException | EntityNotFoundException e ) {

            response.setSuccess( false );
            response.setMessage( e.getMessage() );
            response.setErrorCode( Arrays.toString( e.getStackTrace() ) );

            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );

        }

    }


    @Operation( summary = "API to get all existing Organizations.",
            description = "This API retrieves all existing Organizations." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = OrganizationResponse.class ),
                    mediaType = "application/json" ) } )
    @GetMapping( "/getAll" )
    public ResponseEntity<ApiResponse<List<OrganizationResponse>>> getAllUsersByGroupId() {

        ApiResponse<List<OrganizationResponse>> response = new ApiResponse<>();
        response.setSuccess( true );
        response.setMessage( "Successfully retrieved all Organizations." );
        response.setResponse( organizationService.findAllOrganizations() );

        return new ResponseEntity<>( response, HttpStatus.OK );

    }


    @Operation( summary = "API to delete an Organization by Id.",
            description = "This API deletes an existing Organization based on Organization Id." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = String.class ), mediaType = "application/json" ) } )
    @PostMapping( "/deleteById" )
    public ResponseEntity<ApiResponse<String>> deleteById(
            @RequestParam( name = "orgId" ) String orgId ) {

        ApiResponse<String> response = new ApiResponse<>();
        try {

            response.setSuccess( true );
            response.setMessage( "Successfully deleted Organization." );
            response.setResponse( organizationService.deleteOrganizationById( orgId ) );

            return new ResponseEntity<>( response, HttpStatus.OK );

        } catch( InvalidInputException | AppException | EntityNotFoundException e ) {

            response.setSuccess( false );
            response.setMessage( e.getMessage() );
            response.setErrorCode( Arrays.toString( e.getStackTrace() ) );

            return new ResponseEntity<>( response, HttpStatus.INTERNAL_SERVER_ERROR );

        }

    }


}
