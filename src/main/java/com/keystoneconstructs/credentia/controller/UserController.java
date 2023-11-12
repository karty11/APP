package com.keystoneconstructs.credentia.controller;


import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
import com.keystoneconstructs.credentia.exception.InvalidInputException;
import com.keystoneconstructs.credentia.model.ApiResponse;
import com.keystoneconstructs.credentia.model.UserRequest;
import com.keystoneconstructs.credentia.model.UserResponse;
import com.keystoneconstructs.credentia.service.UserService;
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
@Tag( name = "User Controller", description = "Controller for all User API endpoints." )
@RequestMapping( "/api/v1/user" )
public class UserController {

    @Autowired
    UserService userService;


    @Operation( summary = "API to create new User.",
            description = "This API creates a new User based on User Request." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = UserResponse.class ), mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @PutMapping( "/add" )
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @RequestBody UserRequest userRequest ) throws InvalidInputException, AppException {

        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setSuccess( true );
        response.setMessage( "Successfully created new User." );
        response.setResponse( userService.createUser( userRequest ) );

        return new ResponseEntity<>( response, HttpStatus.CREATED );

    }


    @Operation( summary = "API to login an existing User using User Email and Password.",
            description = "This API log's in an existing User with the given Email and Password." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = String.class ), mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @PutMapping( "/login" )
    public ResponseEntity<ApiResponse<String>> createUser( String email,
            String password ) throws InvalidInputException, AppException, EntityNotFoundException {

        ApiResponse<String> response = new ApiResponse<>();
        response.setSuccess( true );
        response.setMessage( "Successfully logged in." );
        response.setResponse( userService.loginUser( email, password ) );

        return new ResponseEntity<>( response, HttpStatus.OK );

    }


    @Operation( summary = "API to update existing User.",
            description = "This API updates an existing User based on User Id and User Request." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = UserResponse.class ), mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @SecurityRequirement( name = "credentia_auth" )
    @PostMapping( "/edit" )
    public ResponseEntity<ApiResponse<UserResponse>> updateUser( @RequestParam( name = "userId" ) String userId,
            @RequestBody UserRequest userRequest ) throws InvalidInputException, AppException, EntityNotFoundException {

        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setSuccess( true );
        response.setMessage( "Successfully updated User." );
        response.setResponse( userService.updateUser( userRequest, userId ) );

        return new ResponseEntity<>( response, HttpStatus.OK );

    }


    @Operation( summary = "API to update existing User account Password.",
            description = "This API updates an existing User account password based on User Id." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = UserResponse.class ), mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @PostMapping( "/resetPassword" )
    public ResponseEntity<ApiResponse<UserResponse>> updateUserPassword( @RequestParam( name = "userId" ) String userId,
            @RequestParam( name = "oldPassword" ) String oldPassword, @RequestParam( name = "newPassword" )
    String newPassword ) throws InvalidInputException, AppException, EntityNotFoundException {

        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setSuccess( true );
        response.setMessage( "Successfully updated User Password." );
        response.setResponse( userService.updatePassword( userId, oldPassword, newPassword ) );

        return new ResponseEntity<>( response, HttpStatus.OK );

    }


    @Operation( summary = "API to get existing User by Id.",
            description = "This API retrieves an existing User based on User Id." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = UserResponse.class ), mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @SecurityRequirement( name = "credentia_auth" )
    @GetMapping( "/getById" )
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @RequestParam( name = "userId" ) String userId ) throws InvalidInputException, EntityNotFoundException {

        ApiResponse<UserResponse> response = new ApiResponse<>();

        response.setSuccess( true );
        response.setMessage( "Successfully retrieved User." );
        response.setResponse( userService.findUserById( userId ) );

        return new ResponseEntity<>( response, HttpStatus.OK );

    }


    @Operation( summary = "API to get existing User by Email.",
            description = "This API retrieves an existing User based on User Email." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = UserResponse.class ), mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @SecurityRequirement( name = "credentia_auth" )
    @GetMapping( "/getByEmail" )
    public ResponseEntity<ApiResponse<UserResponse>> getUserByEmail(
            @RequestParam( name = "email" ) String email ) throws InvalidInputException, EntityNotFoundException {

        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setSuccess( true );
        response.setMessage( "Successfully retrieved User." );
        response.setResponse( userService.findUserByEmail( email ) );

        return new ResponseEntity<>( response, HttpStatus.OK );

    }


    @Operation( summary = "API to get all existing Users by User Group Id.",
            description = "This API retrieves all existing Users based on User Group Id." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = UserResponse.class ), mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @SecurityRequirement( name = "credentia_auth" )
    @GetMapping( "/getAllByGroupId" )
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsersByGroupId(
            @RequestParam( name = "groupId" ) String groupId ) throws InvalidInputException, EntityNotFoundException {

        ApiResponse<List<UserResponse>> response = new ApiResponse<>();
        response.setSuccess( true );
        response.setMessage( "Successfully retrieved all Users." );
        response.setResponse( userService.findAllUsersByUserGroupId( groupId ) );

        return new ResponseEntity<>( response, HttpStatus.OK );
    }


    @Operation( summary = "API to get all existing Users by Organization Id.",
            description = "This API retrieves all existing Users based on Organization Id." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = UserResponse.class ), mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @SecurityRequirement( name = "credentia_auth" )
    @GetMapping( "/getAllByOrg" )
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsersByOrg(
            @RequestParam( name = "orgId" ) String orgId ) throws InvalidInputException, EntityNotFoundException {

        ApiResponse<List<UserResponse>> response = new ApiResponse<>();
        response.setSuccess( true );
        response.setMessage( "Successfully retrieved all Users." );
        response.setResponse( userService.findAllUsersByOrganizationId( orgId ) );

        return new ResponseEntity<>( response, HttpStatus.OK );

    }


    @Operation( summary = "API to delete a User by Id.",
            description = "This API deletes an existing User based on User Id." )
    @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200", content = {
            @Content( schema = @Schema( implementation = String.class ), mediaType = "application/json" ) } )
    @CrossOrigin( origins = "*", allowedHeaders = "*" )
    @SecurityRequirement( name = "credentia_auth" )
    @PostMapping( "/deleteById" )
    public ResponseEntity<ApiResponse<String>> deleteById( @RequestParam( name = "userId" )
    String userId ) throws InvalidInputException, AppException, EntityNotFoundException {

        ApiResponse<String> response = new ApiResponse<>();
        response.setSuccess( true );
        response.setMessage( "Successfully deleted User." );
        response.setResponse( userService.deleteUserById( userId ) );

        return new ResponseEntity<>( response, HttpStatus.OK );
    }


}
