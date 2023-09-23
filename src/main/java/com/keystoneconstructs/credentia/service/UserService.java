package com.keystoneconstructs.credentia.service;

import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
import com.keystoneconstructs.credentia.exception.InvalidInputException;
import com.keystoneconstructs.credentia.model.UserRequest;
import com.keystoneconstructs.credentia.model.UserResponse;

import java.util.List;

public interface UserService {

    /**
     * This service method creates a new User from a User Request object.
     * @param userRequest
     * @return userResponse
     * @throws InvalidInputException
     * @throws AppException
     */
    UserResponse createUser( UserRequest userRequest ) throws InvalidInputException, AppException;

    /**
     * This service method updates an existing User from a User Request Object per userId.
     * @param userRequest
     * @param userId
     * @return UserResponse
     * @throws InvalidInputException
     * @throws EntityNotFoundException
     * @throws AppException
     */
    UserResponse updateUser( UserRequest userRequest, String userId ) throws InvalidInputException, EntityNotFoundException, AppException;

    /**
     * This service method uses a User Id to retrieve a User.
     * @param userId
     * @return userResponse
     * @throws InvalidInputException
     * @throws EntityNotFoundException
     */
    UserResponse findUserById( String userId ) throws InvalidInputException, EntityNotFoundException;

    /**
     * This service method uses an Email Address to retrieve a User.
     * @param email
     * @return userResponse
     * @throws InvalidInputException
     * @throws EntityNotFoundException
     */
    UserResponse findUserByEmail( String email ) throws InvalidInputException, EntityNotFoundException;

    /**
     * This service method uses a User Group Id to return a list of Users belonging to the group.
     * @param userGroupId
     * @return userResponses
     * @throws InvalidInputException
     * @throws EntityNotFoundException
     */
    List<UserResponse> findAllUsersByUserGroupId( String userGroupId ) throws InvalidInputException, EntityNotFoundException;

    /**
     * This service method uses an Organization Id to get a list of Users belonging to the Organization.
     * @param orgId
     * @return userResponses
     * @throws InvalidInputException
     * @throws EntityNotFoundException
     */
    List<UserResponse> findAllUsersByOrganizationId( String orgId ) throws InvalidInputException, EntityNotFoundException;

    /**
     * This service method deletes an existing user based on User Id.
     * @param id
     * @return Success
     * @throws InvalidInputException
     * @throws EntityNotFoundException
     * @throws AppException
     */
    String deleteUserById( String id ) throws InvalidInputException, EntityNotFoundException, AppException;

}
