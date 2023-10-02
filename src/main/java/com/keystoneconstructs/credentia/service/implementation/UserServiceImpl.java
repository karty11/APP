package com.keystoneconstructs.credentia.service.implementation;

import com.keystoneconstructs.credentia.Utils.EncryptionUtils;
import com.keystoneconstructs.credentia.constant.Constants;
import com.keystoneconstructs.credentia.constant.Role;
import com.keystoneconstructs.credentia.converters.Converter;
import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
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

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
                StringUtils.isEmpty( userRequest.getOrganizationRequest().getOrganizationName() ) ||
                StringUtils.isEmpty( userRequest.getPassword() ) ) {

            log.error( "Invalid Inputs provides. Please check User Request Body." );
            throw new InvalidInputException( "Invalid Inputs provides. Please check User Request Body." );

        }

        Optional<UserEntity> user = userRepository.findByEmailIgnoreCase( userRequest.getEmail() );

        if( user.isPresent() ) {
            log.error( "User with given email already exists." );
            throw new AppException( "User with given email already exists." );
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

            userEntity.setSalt( EncryptionUtils.getNewSalt() );
            userEntity.setEncryptedPassword(
                    EncryptionUtils.getEncryptedPassword( userRequest.getPassword(), userEntity.getSalt() ) );

        } catch( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            log.error( e.getMessage() );
            throw new AppException( e.getMessage() );
        }

        try {

            log.info( "User Created with Id -> " + userEntity.getId() );
            return Converter.convertUserEntityToResponse( userRepository.save( userEntity ) );

        } catch( Exception e ) {
            log.error( "Failed to add new User." );
            throw new AppException( "Failed to add new User." );
        }

    }


    @Override
    public UserResponse updateUser( UserRequest userRequest,
            String userId ) throws InvalidInputException, EntityNotFoundException, AppException {

        if( userRequest == null || StringUtils.isEmpty( userId ) ) {
            log.error( "Invalid Inputs provides. Please check User Request Body and User Id." );
            throw new InvalidInputException( "Invalid Inputs provides. Please check User Request Body and User Id." );
        }

        Optional<UserEntity> user = userRepository.findById( userId );

        if( user.isEmpty() ) {
            log.error( "User with Id " + userId + " was not found." );
            throw new EntityNotFoundException( "User with Id " + userId + " was not found." );
        }

        UserEntity userEntity = user.get();

        if( userEntity.isDeleted() ) {
            log.error( "User with Id " + userId + " is not active." );
            throw new EntityNotFoundException( "User with Id " + userId + " is not active." );
        }

        log.info( "Updating user with id -> " + userId );

        verifyUpdateUserEntity( userEntity, userRequest );

        try {
            log.info( "Updated user with id -> " + userId );
            return Converter.convertUserEntityToResponse( userRepository.save( userEntity ) );
        } catch( Exception e ) {
            log.error( "Failed to update User." );
            throw new AppException( "Failed to update User." );
        }

    }

    @Override
    public UserResponse updatePassword( String userId, String oldPassword,
            String newPassword ) throws InvalidInputException, EntityNotFoundException, AppException {

        if( StringUtils.isEmpty( userId ) || StringUtils.isEmpty( oldPassword ) ||
                StringUtils.isEmpty( newPassword ) ) {
            log.error( "Invalid inputs. Please verify the input fields." );
            throw new InvalidInputException( "Invalid inputs. Please verify the input fields." );
        }

        Optional<UserEntity> user = userRepository.findById( userId );

        if( user.isEmpty() ) {
            log.error( "User with id " + userId + " was not found." );
            throw new EntityNotFoundException( "User with id " + userId + " was not found." );
        }

        UserEntity userEntity = user.get();

        try {

            if( !userEntity.getEncryptedPassword()
                    .equals( EncryptionUtils.getEncryptedPassword( oldPassword, userEntity.getSalt() ) ) ) {
                log.error( "Invalid Current Password entered." );
                throw new InvalidInputException( "Invalid Current Password entered." );
            }

            userEntity.setSalt( EncryptionUtils.getNewSalt() );
            userEntity.setEncryptedPassword(
                    EncryptionUtils.getEncryptedPassword( newPassword, userEntity.getSalt() ) );

            log.info( "Successfully updated Password for User with id " + userId + "." );

            return Converter.convertUserEntityToResponse( userRepository.save( userEntity ) );

        } catch( Exception e ) {
            throw new AppException( e.getMessage() );
        }

    }

    @Override
    public UserResponse findUserById( String userId ) throws InvalidInputException, EntityNotFoundException {

        if( StringUtils.isEmpty( userId ) ) {
            log.error( "User Id cannot be empty or null." );
            throw new InvalidInputException( "User Id cannot be empty or null." );
        }

        Optional<UserEntity> user = userRepository.findById( userId );

        if( user.isEmpty() ) {
            log.error( "User with Id " + userId + " was not found." );
            throw new EntityNotFoundException( "User with Id " + userId + " was not found." );
        }

        return Converter.convertUserEntityToResponse( user.get() );

    }


    @Override
    public UserResponse findUserByEmail( String email ) throws InvalidInputException, EntityNotFoundException {

        if( StringUtils.isEmpty( email ) ) {
            log.error( "Email cannot be empty or null." );
            throw new InvalidInputException( "Email cannot be empty or null." );
        }

        Optional<UserEntity> user = userRepository.findByEmailIgnoreCase( email );

        if( user.isEmpty() ) {
            log.error( "User with email " + email + " was not found." );
            throw new EntityNotFoundException( "User with email " + email + " was not found." );
        }

        return Converter.convertUserEntityToResponse( user.get() );

    }


    @Override
    public List<UserResponse> findAllUsersByUserGroupId(
            String userGroupId ) throws InvalidInputException, EntityNotFoundException {

        if( StringUtils.isEmpty( userGroupId ) ) {
            log.error( "User Group Id cannot be empty or null." );
            throw new InvalidInputException( "User Group Id cannot be empty or null." );
        }

        List<UserEntity> users = userRepository.findAllByUserGroupId( userGroupId );

        if( users.isEmpty() ) {
            log.error( "No Users with User Group Id " + userGroupId + " were not found." );
            throw new EntityNotFoundException( "No Users with User Group Id " + userGroupId + " were not found." );
        }

        return users.stream().map( Converter::convertUserEntityToResponse ).toList();

    }


    @Override
    public List<UserResponse> findAllUsersByOrganizationId(
            String orgId ) throws InvalidInputException, EntityNotFoundException {

        if( StringUtils.isEmpty( orgId ) ) {
            log.error( "Organization Id cannot be empty or null." );
            throw new InvalidInputException( "Organization Id cannot be empty or null." );
        }

        Optional<OrganizationEntity> organization = organizationRepository.findById( orgId );

        if( organization.isEmpty() ) {
            log.error( "Organization with Id " + orgId + " was not found." );
            throw new EntityNotFoundException( "Organization with Id " + orgId + " was not found." );
        }

        List<UserEntity> userEntities = userRepository.findAllByOrganization_id( orgId );

        if( userEntities.isEmpty() ) {
            log.error( "No Users found for Organization Id " + orgId + " were found." );
            throw new EntityNotFoundException( "No Users found for Organization Id " + orgId + " were found." );
        }

        return userEntities.stream().map( Converter::convertUserEntityToResponse ).toList();

    }


    @Override
    public String deleteUserById( String id ) throws InvalidInputException, EntityNotFoundException, AppException {

        if( StringUtils.isEmpty( id ) ) {
            log.error( "User Id cannot be empty or null." );
            throw new InvalidInputException( "User Id cannot be empty or null." );
        }

        Optional<UserEntity> user = userRepository.findById( id );

        if( user.isEmpty() ) {
            log.error( "User with id " + id + " was not found." );
            throw new EntityNotFoundException( "User with id " + id + " was not found." );
        }

        try {
            userRepository.delete( user.get() );
        } catch( Exception e ) {
            log.error( "Failed to delete User with id " + id + "." );
            throw new AppException( "Failed to delete User with id " + id + "." );
        }
        return Constants.SUCCESS;

    }


    /*--------------------------------------------------------
     ------------------ Private Methods ----------------------
     --------------------------------------------------------*/

    /**
     * This method verifies and supplies User Request fields to User Entity.
     * @param userEntity - User Entity object
     * @param userRequest - User Request object
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
