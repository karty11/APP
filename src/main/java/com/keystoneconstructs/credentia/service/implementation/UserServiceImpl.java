package com.keystoneconstructs.credentia.service.implementation;

import com.keystoneconstructs.credentia.constant.Constants;
import com.keystoneconstructs.credentia.constant.Role;
import com.keystoneconstructs.credentia.converters.Converter;
import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
import com.keystoneconstructs.credentia.exception.ErrorCodeAndMessage;
import com.keystoneconstructs.credentia.exception.InvalidInputException;
import com.keystoneconstructs.credentia.model.UserRequest;
import com.keystoneconstructs.credentia.model.UserResponse;
import com.keystoneconstructs.credentia.pojo.OrganizationEntity;
import com.keystoneconstructs.credentia.pojo.UserEntity;
import com.keystoneconstructs.credentia.repository.OrganizationRepository;
import com.keystoneconstructs.credentia.repository.UserRepository;
import com.keystoneconstructs.credentia.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Override
    public UserResponse createUser( UserRequest userRequest ) throws InvalidInputException, AppException {

        if( userRequest == null || StringUtils.isEmpty( userRequest.getRole() ) ||
                StringUtils.isEmpty( userRequest.getFirstName() ) || StringUtils.isEmpty( userRequest.getLastName() ) ||
                userRequest.getOrganizationRequest() == null ||
                StringUtils.isEmpty( userRequest.getOrganizationRequest().getOrganizationName() ) ) {
            log.error( ErrorCodeAndMessage.INVALID_INPUT_EXCEPTION.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.INVALID_INPUT_EXCEPTION );
        }

        Optional<UserEntity> user = userRepository.findByEmailIgnoreCase( userRequest.getEmail() );

        if( user.isPresent() ) {
            log.error( ErrorCodeAndMessage.DUPLICATE_USER_EMAIL.getMessage() );
            throw new AppException( ErrorCodeAndMessage.DUPLICATE_USER_EMAIL );
        }

        log.info( "Creating user." );

        UserEntity userEntity = new UserEntity();

        //        OrganizationEntity organization = organizationRepository.findById( userRequest.getOrganizationId() )
        //                .orElseThrow( () -> new EntityNotFoundException(
        //                        "Organization with id " + userRequest.getOrganizationId() + " was not found." ) );

        Optional<OrganizationEntity> organization = organizationRepository.findByNameIgnoreCase(
                userRequest.getOrganizationRequest().getOrganizationName() );

        OrganizationEntity organizationEntity;

        if( organization.isEmpty() ) {
            organizationEntity = new OrganizationEntity();
            organizationEntity.setId( UUID.randomUUID().toString() );
            organizationEntity.setName( userRequest.getOrganizationRequest().getOrganizationName() );

            if( StringUtils.isNotEmpty( userRequest.getOrganizationRequest().getOrganizationIndustry() ) ) {
                organizationEntity.setIndustry( userRequest.getOrganizationRequest().getOrganizationIndustry() );
            }

            organizationEntity = organizationRepository.save( organizationEntity );

        } else {
            organizationEntity = organization.get();
        }

        userEntity.setId( UUID.randomUUID().toString() );
        userEntity.setOrganization( organizationEntity );
        userEntity.setFirstName( userRequest.getFirstName() );
        userEntity.setLastName( userRequest.getLastName() );
        userEntity.setDeleted( false );
        userEntity.setRole( Role.valueOf( userRequest.getRole() ).name() );

        if( StringUtils.isNotEmpty( userRequest.getInitials() ) ) {
            userEntity.setInitials( userRequest.getInitials() );
        }

        if( StringUtils.isNotEmpty( userRequest.getEmail() ) ) {
            userEntity.setEmail( userRequest.getEmail() );
        }

        if( userRequest.getContact() != null ) {
            userEntity.setContactEntity( Converter.convertContactToEntity( userRequest.getContact() ) );
        }

        try {
            log.info( "User Created with Id -> " + userEntity.getId() );
            return Converter.convertUserEntityToResponse( userRepository.save( userEntity ) );
        } catch( Exception e ) {
            log.error( ErrorCodeAndMessage.FAILED_SAVE_USER.getMessage() );
            throw new AppException( ErrorCodeAndMessage.FAILED_SAVE_USER );
        }

    }


    @Override
    public UserResponse updateUser( UserRequest userRequest,
            String userId ) throws InvalidInputException, EntityNotFoundException, AppException {

        if( userRequest == null || StringUtils.isEmpty( userId ) ) {
            log.error( ErrorCodeAndMessage.INVALID_INPUT_EXCEPTION.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.INVALID_INPUT_EXCEPTION );
        }

        Optional<UserEntity> user = userRepository.findById( userId );

        if( user.isEmpty() ) {
            log.error( ErrorCodeAndMessage.USER_ID_NOT_FOUND.getMessage() + "\n" + Constants.ID + " : " + userId );
            throw new EntityNotFoundException( ErrorCodeAndMessage.USER_ID_NOT_FOUND );
        }

        UserEntity userEntity = user.get();

        if( userEntity.isDeleted() ) {
            log.error( ErrorCodeAndMessage.USER_ID_NOT_FOUND.getMessage() + "\n" + Constants.ID + " : " + userId +
                    " deactivated." );
            throw new EntityNotFoundException( ErrorCodeAndMessage.USER_ID_NOT_FOUND );
        }

        log.info( "Updating user with id -> " + userId );

        verifyUpdateUserEntity( userEntity, userRequest );

        try {
            log.info( "Updated user with id -> " + userId );
            return Converter.convertUserEntityToResponse( userRepository.save( userEntity ) );
        } catch( Exception e ) {
            log.error( ErrorCodeAndMessage.FAILED_SAVE_USER.getMessage() );
            throw new AppException( ErrorCodeAndMessage.FAILED_SAVE_USER );
        }

    }


    @Override
    public UserResponse findUserById( String userId ) throws InvalidInputException, EntityNotFoundException {

        if( StringUtils.isEmpty( userId ) ) {
            log.error( ErrorCodeAndMessage.USER_ID_MISSING.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.USER_ID_MISSING );
        }

        Optional<UserEntity> user = userRepository.findById( userId );

        if( user.isEmpty() ) {
            log.error( ErrorCodeAndMessage.USER_ID_NOT_FOUND.getMessage() + "\n" + Constants.ID + " : " + userId );
            throw new EntityNotFoundException( ErrorCodeAndMessage.USER_ID_NOT_FOUND );
        }

        return Converter.convertUserEntityToResponse( user.get() );

    }


    @Override
    public UserResponse findUserByEmail( String email ) throws InvalidInputException, EntityNotFoundException {

        if( StringUtils.isEmpty( email ) ) {
            log.error( ErrorCodeAndMessage.USER_EMAIL_MISSING.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.USER_EMAIL_MISSING );
        }

        Optional<UserEntity> user = userRepository.findByEmailIgnoreCase( email );

        if( user.isEmpty() ) {
            log.error( ErrorCodeAndMessage.USER_EMAIL_NOT_FOUND.getMessage() + "\n" + Constants.EMAIL + " : " + email );
            throw new EntityNotFoundException( ErrorCodeAndMessage.USER_EMAIL_NOT_FOUND );
        }

        return Converter.convertUserEntityToResponse( user.get() );

    }


    @Override
    public List<UserResponse> findAllUsersByUserGroupId(
            String userGroupId ) throws InvalidInputException, EntityNotFoundException {

        if( StringUtils.isEmpty( userGroupId ) ) {
            log.error( ErrorCodeAndMessage.INVALID_INPUT_EXCEPTION.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.INVALID_INPUT_EXCEPTION );
        }

        List<UserEntity> users = userRepository.findAllByUserGroupId( userGroupId );

        if( users.isEmpty() ) {
            log.error( ErrorCodeAndMessage.USER_GROUP_EMPTY.getMessage() + "\n" + Constants.ID + " : " + userGroupId );
            return Collections.emptyList();
        }

        return users.stream().map( Converter::convertUserEntityToResponse ).toList();

    }


    @Override
    public List<UserResponse> findAllUsersByOrganizationId(
            String orgId ) throws InvalidInputException, EntityNotFoundException {

        if( StringUtils.isEmpty( orgId ) ) {
            log.error( ErrorCodeAndMessage.ORGANIZATION_ID_MISSING.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.ORGANIZATION_ID_MISSING );
        }

        Optional<OrganizationEntity> organization = organizationRepository.findById( orgId );

        if( organization.isEmpty() ) {
            log.error( ErrorCodeAndMessage.ORGANIZATION_ID_NOT_FOUND.getMessage() + "\n" + Constants.ORG_ID + " : " + orgId );
            throw new EntityNotFoundException( ErrorCodeAndMessage.ORGANIZATION_ID_NOT_FOUND );
        }

        List<UserEntity> userEntities = userRepository.findAllByOrganization_id( orgId );

        if( userEntities.isEmpty() ) {
            log.error( ErrorCodeAndMessage.USER_GROUP_EMPTY.getMessage() + "\n" + Constants.ORG_ID + " : " + orgId );
            return Collections.emptyList();
        }

        return userEntities.stream().map( Converter::convertUserEntityToResponse ).toList();

    }


    @Override
    public String deleteUserById( String id ) throws InvalidInputException, EntityNotFoundException, AppException {

        if( StringUtils.isEmpty( id ) ) {
            log.error( ErrorCodeAndMessage.USER_ID_MISSING.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.USER_ID_MISSING );
        }

        Optional<UserEntity> user = userRepository.findById( id );

        if( user.isEmpty() ) {
            log.error( ErrorCodeAndMessage.USER_ID_NOT_FOUND.getMessage() + "\n"  + Constants.ID + " : " + id );
            throw new EntityNotFoundException( ErrorCodeAndMessage.USER_ID_NOT_FOUND );
        }

        try {
            userRepository.delete( user.get() );
        } catch( Exception e ) {
            log.error( ErrorCodeAndMessage.FAILED_DELETE_USER.getMessage()+ "\n"  + Constants.ID + " : " + id );
            throw new AppException( ErrorCodeAndMessage.FAILED_DELETE_USER );
        }
        return Constants.SUCCESS;

    }


    /***************************************************************
     ******************* Private Methods ***************************
     ***************************************************************/

    /**
     * This method verifies and supplies User Request fields to User Entity.
     * @param userEntity
     * @param userRequest
     */
    private void verifyUpdateUserEntity( UserEntity userEntity, UserRequest userRequest ) {

        if( StringUtils.isNotEmpty( userRequest.getFirstName() ) &&
                !userEntity.getFirstName().equals( userRequest.getFirstName() ) ) {
            userEntity.setFirstName( userRequest.getFirstName() );
        }

        if( StringUtils.isNotEmpty( userRequest.getLastName() ) &&
                !userEntity.getLastName().equals( userRequest.getLastName() ) ) {
            userEntity.setLastName( userRequest.getLastName() );
        }

        if( StringUtils.isNotEmpty( userRequest.getRole() ) && !userEntity.getRole().equals( userRequest.getRole() ) ) {
            userEntity.setRole( userRequest.getRole() );
        }

        if( StringUtils.isEmpty( userEntity.getInitials() ) || ( StringUtils.isNotEmpty( userRequest.getInitials() ) &&
                !userEntity.getInitials().equals( userRequest.getInitials() ) ) ) {
            userEntity.setInitials( userRequest.getInitials() );
        }

        if( StringUtils.isEmpty( userEntity.getEmail() ) || ( StringUtils.isNotEmpty( userRequest.getEmail() ) &&
                !userEntity.getEmail().equals( userRequest.getEmail() ) ) ) {
            userEntity.setEmail( userRequest.getEmail() );
        }

        if( userRequest.getContact() != null ) {
            userEntity.setContactEntity( Converter.convertContactToEntity( userRequest.getContact() ) );
        }

    }


}
