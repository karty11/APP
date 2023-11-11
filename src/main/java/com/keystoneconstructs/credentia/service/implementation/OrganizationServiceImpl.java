package com.keystoneconstructs.credentia.service.implementation;

import com.keystoneconstructs.credentia.constant.Constants;
import com.keystoneconstructs.credentia.converters.Converter;
import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
import com.keystoneconstructs.credentia.exception.ErrorCodeAndMessage;
import com.keystoneconstructs.credentia.exception.InvalidInputException;
import com.keystoneconstructs.credentia.model.OrganizationRequest;
import com.keystoneconstructs.credentia.model.OrganizationResponse;
import com.keystoneconstructs.credentia.pojo.OrganizationEntity;
import com.keystoneconstructs.credentia.repository.OrganizationRepository;
import com.keystoneconstructs.credentia.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;

    @Override
    public OrganizationResponse createOrgainzation(
            OrganizationRequest organizationRequest ) throws InvalidInputException, AppException {

        if( organizationRequest == null || StringUtils.isEmpty( organizationRequest.getName() ) ||
                StringUtils.isEmpty( organizationRequest.getOrgCode() ) ) {
            log.error( ErrorCodeAndMessage.INVALID_INPUT_EXCEPTION.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.INVALID_INPUT_EXCEPTION );
        }

        Optional<OrganizationEntity> organization = organizationRepository.findByNameIgnoreCase(
                organizationRequest.getName() );

        if( organization.isPresent() ) {
            log.error(
                    ErrorCodeAndMessage.DUPLICATE_ORGANIZATION_NAME.getMessage() + "\n" + Constants.ORG_NAME + " : " +
                            organizationRequest.getName() );
            throw new AppException( ErrorCodeAndMessage.DUPLICATE_ORGANIZATION_NAME );
        }

        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setId( UUID.randomUUID().toString() );
        organizationEntity.setName( organizationRequest.getName() );
        organizationEntity.setOrgCode( organizationRequest.getOrgCode() );

        if( StringUtils.isNotEmpty( organizationRequest.getIndustry() ) ) {
            organizationEntity.setIndustry( organizationRequest.getIndustry() );
        }

        if( organizationRequest.getContact() != null ) {
            organizationEntity.setContact( Converter.convertContactToEntity( organizationRequest.getContact() ) );
        }

        try {
            return Converter.convertOrganizationEntityToResponse( organizationRepository.save( organizationEntity ) );
        } catch( Exception e ) {
            log.error( ErrorCodeAndMessage.FAILED_SAVE_ORGANIZATION.getMessage() );
            throw new AppException( ErrorCodeAndMessage.FAILED_SAVE_ORGANIZATION );
        }

    }

    @Override
    public OrganizationResponse updateOrganization( OrganizationRequest organizationRequest,
            String organizationId ) throws InvalidInputException, EntityNotFoundException, AppException {

        if( StringUtils.isEmpty( organizationId ) || organizationRequest == null ) {
            log.info( ErrorCodeAndMessage.INVALID_INPUT_EXCEPTION.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.INVALID_INPUT_EXCEPTION );
        }

        Optional<OrganizationEntity> organization = organizationRepository.findById( organizationId );

        if( organization.isEmpty() ) {
            log.error( ErrorCodeAndMessage.ORGANIZATION_ID_NOT_FOUND.getMessage() + "\n" + Constants.ORG_ID + " : " +
                    organizationId );
            throw new EntityNotFoundException( ErrorCodeAndMessage.ORGANIZATION_ID_NOT_FOUND );
        }

        OrganizationEntity organizationEntity = organization.get();

        if( StringUtils.isEmpty( organizationEntity.getName() ) ||
                ( StringUtils.isNotEmpty( organizationRequest.getName() ) ) &&
                        !organizationRequest.getName().equals( organizationEntity.getName() ) ) {
            organizationEntity.setName( organizationRequest.getName() );
        }

        if( StringUtils.isEmpty( organizationEntity.getIndustry() ) ||
                ( StringUtils.isNotEmpty( organizationRequest.getIndustry() ) &&
                        !organizationRequest.getIndustry().equals( organizationEntity.getIndustry() ) ) ) {
            organizationEntity.setIndustry( organizationRequest.getIndustry() );
        }

        if( StringUtils.isNotEmpty( organizationEntity.getOrgCode() ) ||
                ( StringUtils.isNotEmpty( organizationRequest.getOrgCode() ) &&
                        !organizationRequest.getOrgCode().equals( organizationEntity.getOrgCode() ) ) ) {
            organizationEntity.setOrgCode( organizationRequest.getOrgCode() );
        }

        if( organizationRequest.getContact() != null ) {
            organizationEntity.setContact( Converter.convertContactToEntity( organizationRequest.getContact() ) );
        }

        try {
            return Converter.convertOrganizationEntityToResponse( organizationRepository.save( organizationEntity ) );
        } catch( Exception e ) {
            log.error( ErrorCodeAndMessage.FAILED_SAVE_ORGANIZATION.getMessage() + "\n" + Constants.ORG_ID + " : " +
                    organizationId );
            throw new AppException( ErrorCodeAndMessage.FAILED_SAVE_ORGANIZATION );
        }

    }

    @Override
    public OrganizationResponse findOrganizationById(
            String organizationId ) throws EntityNotFoundException, InvalidInputException {

        if( StringUtils.isEmpty( organizationId ) ) {
            log.error( ErrorCodeAndMessage.ORGANIZATION_ID_MISSING.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.ORGANIZATION_ID_MISSING );
        }

        Optional<OrganizationEntity> organization = organizationRepository.findById( organizationId );

        if( organization.isEmpty() ) {
            log.error( ErrorCodeAndMessage.ORGANIZATION_ID_NOT_FOUND.getMessage() + "\n" + Constants.ORG_ID + " : " +
                    organizationId );
            throw new EntityNotFoundException( ErrorCodeAndMessage.ORGANIZATION_ID_NOT_FOUND );
        }

        return Converter.convertOrganizationEntityToResponse( organization.get() );

    }

    @Override
    public List<OrganizationResponse> findAllOrganizations() {

        List<OrganizationEntity> organizations = organizationRepository.findAll();

        if( organizations.isEmpty() ) {
            return Collections.emptyList();
        }

        return organizations.stream().map( Converter::convertOrganizationEntityToResponse ).toList();

    }

    @Override
    public String deleteOrganizationById(
            String organizationId ) throws InvalidInputException, EntityNotFoundException, AppException {

        if( StringUtils.isEmpty( organizationId ) ) {
            log.error( ErrorCodeAndMessage.ORGANIZATION_ID_MISSING.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.ORGANIZATION_ID_MISSING );
        }

        Optional<OrganizationEntity> organization = organizationRepository.findById( organizationId );

        if( organization.isEmpty() ) {
            log.error( ErrorCodeAndMessage.ORGANIZATION_ID_NOT_FOUND.getMessage() + "\n" + Constants.ORG_ID + " : " +
                    organizationId );
            throw new EntityNotFoundException( ErrorCodeAndMessage.ORGANIZATION_ID_NOT_FOUND );
        }

        try {
            organizationRepository.delete( organization.get() );
        } catch( Exception e ) {
            log.error( ErrorCodeAndMessage.FAILED_DELETE_ORGANIZATION.getMessage() + "\n" + Constants.ORG_ID + " : " +
                    organizationId );
            throw new AppException( ErrorCodeAndMessage.FAILED_DELETE_ORGANIZATION );
        }

        return Constants.SUCCESS;

    }


    /***************************************************************
     ******************* Private Methods ***************************
     ***************************************************************/

    private String getString(String string){
        return string;
    }

}
