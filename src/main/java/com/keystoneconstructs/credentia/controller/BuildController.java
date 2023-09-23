package com.keystoneconstructs.credentia.controller;

import com.keystoneconstructs.credentia.model.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag( name = "Build Test Controller", description = "Controller to test Build related APIs.")
@RequestMapping( "/api/v1/build" )
public class BuildController {

    @Value( "${environment}" )
    private String environment;

    @Operation( summary = "API to get current environment in use.",
            description = "This API will retrieve current deployed environment." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = String.class ), mediaType = "application/json" ) } )
    @GetMapping( "/environment" )
    public ResponseEntity< ApiResponse< String > > getEnvironment() {

        ApiResponse< String > apiResponse = new ApiResponse<>();
        apiResponse.setResponse( "Environment : " + environment );
        apiResponse.setSuccess( true );
        apiResponse.setMessage( "Successfully recieved deployed environment." );

        return new ResponseEntity<>( apiResponse, HttpStatus.OK );

    }

}
