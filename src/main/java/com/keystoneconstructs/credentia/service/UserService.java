package com.keystoneconstructs.credentia.service;

import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
import com.keystoneconstructs.credentia.exception.InvalidInputException;
import com.keystoneconstructs.credentia.model.UserRequest;
import com.keystoneconstructs.credentia.model.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    /**
     * This service method creates a new User from a User Request object.
     * @param userRequest - User Request Object.
     * @return userResponse - User Response Object.
     * @throws InvalidInputException - Invalid Inputs from User.
     * @throws AppException          - Incomplete conversions or repository operations.
     */
    UserResponse createUser( UserRequest userRequest ) throws InvalidInputException, AppException;

    /**
     * This service method creates a JWT token on valid login details using Email and Password.
     * @param email    - User Email id.
     * @param password - User Password.
     * @return token - JWT Token.
     * @throws InvalidInputException   - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     * @throws AppException            - Incomplete conversions or repository operations.
     */
    String loginUser( String email,
            String password ) throws InvalidInputException, EntityNotFoundException, AppException;

    /**
     * This service method updates an existing User from a User Request Object per userId.
     * @param userRequest - User Request Object.
     * @param userId      - User id.
     * @return UserResponse - User Response Object.
     * @throws InvalidInputException   - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     * @throws AppException            - Incomplete conversions or repository operations.
     */
    UserResponse updateUser( UserRequest userRequest,
            String userId ) throws InvalidInputException, EntityNotFoundException, AppException;

    /**
     * This method updates the password for an existing user.
     * @param userId      - User id.
     * @param oldPassword - Current Password.
     * @param newPassword - New Password.
     * @return userResponse - User Response Object.
     * @throws InvalidInputException   - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     * @throws AppException            - Incomplete conversions or repository operations.
     */
    UserResponse updatePassword( String userId, String oldPassword,
            String newPassword ) throws InvalidInputException, EntityNotFoundException, AppException;

    /**
     * This service method uses a User id to retrieve a User.
     * @param userId - User id.
     * @return userResponse - User Response Object.
     * @throws InvalidInputException   - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     */
    UserResponse findUserById( String userId ) throws InvalidInputException, EntityNotFoundException;

    /**
     * This service method uses an Email Address to retrieve a User.
     * @param email - User Email id.
     * @return userResponse - User Response Object.
     * @throws InvalidInputException   - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     */
    UserResponse findUserByEmail( String email ) throws InvalidInputException, EntityNotFoundException;

    /**
     * This service method uses a User Group id to return a list of Users belonging to the group.
     * @param userGroupId - User Group id.
     * @return userResponses - List of User Response Object.
     * @throws InvalidInputException   - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     */
    List<UserResponse> findAllUsersByUserGroupId(
            String userGroupId ) throws InvalidInputException, EntityNotFoundException;

    /**
     * This service method uses an Organization id to get a list of Users belonging to the Organization.
     * @param orgId - Organization id.
     * @return userResponses - List of User Response Object.
     * @throws InvalidInputException   - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     */
    List<UserResponse> findAllUsersByOrganizationId(
            String orgId ) throws InvalidInputException, EntityNotFoundException;

    /**
     * This service method deletes an existing user based on User id.
     * @param id - User id.
     * @return Success - when object deleted.
     * @throws InvalidInputException   - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     * @throws AppException            - Incomplete conversions or repository operations.
     */
    String deleteUserById( String id ) throws InvalidInputException, EntityNotFoundException, AppException;

}
