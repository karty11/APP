package com.keystoneconstructs.credentia.service;

import com.keystoneconstructs.credentia.model.UserRequest;
import com.keystoneconstructs.credentia.model.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser( UserRequest userRequest );

    UserResponse updateUser( UserRequest userRequest, String userId );

    UserResponse findUserById( String userId );

    UserResponse findUserByEmail( String email );

    List<UserResponse> findAllUsersByUserGroupId( String userGroupId );

    List<UserResponse> findAllUsersByOraginzationId( String userId );

    String deleteUserById( String id );

}
