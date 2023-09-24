package com.keystoneconstructs.credentia.service.implementation;

import com.keystoneconstructs.credentia.constant.Constants;
import com.keystoneconstructs.credentia.converters.Converter;
import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
import com.keystoneconstructs.credentia.exception.InvalidInputException;
import com.keystoneconstructs.credentia.model.CertificateRequest;
import com.keystoneconstructs.credentia.model.CertificateResponse;
import com.keystoneconstructs.credentia.pojo.CertificateEntity;
import com.keystoneconstructs.credentia.pojo.OrganizationEntity;
import com.keystoneconstructs.credentia.repository.CertificateRepository;
import com.keystoneconstructs.credentia.repository.OrganizationRepository;
import com.keystoneconstructs.credentia.repository.UserRepository;
import com.keystoneconstructs.credentia.service.CertificateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    CertificateRepository certificateRepository;


    @Override
    public CertificateResponse addCertificate( CertificateRequest certificateRequest ) throws InvalidInputException, AppException, EntityNotFoundException {

        if( certificateRequest == null || StringUtils.isEmpty(
                certificateRequest.getIssuingAuthority() ) || StringUtils.isEmpty(
                certificateRequest.getCompanyBranding() ) || StringUtils.isEmpty(
                certificateRequest.getName() ) || StringUtils.isEmpty( certificateRequest.getOrganizationId() ) ) {
            log.error( "Please verify the provided Certificate Request Body." );
            throw new InvalidInputException( "Please verify the provided Certificate Request Body." );
        }

        Optional<CertificateEntity> certificate = certificateRepository.findByNameIgnoreCase(
                certificateRequest.getName() );

        if( certificate.isPresent() ) {
            log.error( "Certificate with name " + certificateRequest.getName() + " already exists." );
            throw new InvalidInputException(
                    "Certificate with name " + certificateRequest.getName() + " already exists." );
        }

        Optional<OrganizationEntity> organization = organizationRepository.findById(
                certificateRequest.getOrganizationId() );

        if( organization.isEmpty() ) {
            log.error( "Organization with id " + certificateRequest.getOrganizationId() + " does not exist." );
            throw new EntityNotFoundException(
                    "Organization with id " + certificateRequest.getOrganizationId() + " does not exist." );
        }

        CertificateEntity certificateEntity = new CertificateEntity();
        certificateEntity.setId( UUID.randomUUID().toString() );
        certificateEntity.setIssuingAuthority( certificateRequest.getIssuingAuthority() );
        certificateEntity.setCompanyBranding( certificateRequest.getCompanyBranding() );
        certificateEntity.setName( certificateRequest.getName() );
        certificateEntity.setOrganization( organization.get() );

        if( StringUtils.isNotEmpty( certificateRequest.getCertificationCriteria() ) ) {
            certificateEntity.setCertificationCriteria( certificateRequest.getCertificationCriteria() );
        }

        if( StringUtils.isNotEmpty( certificateRequest.getRevokeCriteria() ) ) {
            certificateEntity.setRevokeCriteria( certificateRequest.getRevokeCriteria() );
        }

        if( StringUtils.isNotEmpty( certificateRequest.getCourseUrl() ) ) {
            certificateEntity.setCourseUrl( certificateRequest.getCourseUrl() );
        }

        if( StringUtils.isNotEmpty( certificateRequest.getWebsite() ) ) {
            certificateEntity.setWebsite( certificateRequest.getWebsite() );
        }

        try {
            return Converter.convertCertificateEntityToResponse( certificateRepository.save( certificateEntity ) );
        } catch( Exception e ) {
            log.error( "Failed to add new Certificate." );
            throw new AppException( "Failed to add new Certificate." );
        }

    }

    @Override
    public CertificateResponse updateCertificate( String certificateId, CertificateRequest certificateRequest ) throws InvalidInputException, EntityNotFoundException, AppException {

        if( StringUtils.isEmpty( certificateId ) || certificateRequest == null ) {
            log.error( "Certificate Id or Certificate Request cannot be null or empty." );
            throw new InvalidInputException( "Certificate Id or Certificate Request cannot be null or empty." );
        }

        Optional<CertificateEntity> certificate = certificateRepository.findById( certificateId );

        if( certificate.isEmpty() ) {
            log.error( "Certificate with id " + certificateId + " was not found." );
            throw new EntityNotFoundException( "Certificate with id " + certificateId + " was not found." );
        }

        CertificateEntity certificateEntity = certificate.get();

        if( StringUtils.isNotEmpty( certificateRequest.getIssuingAuthority() ) ) {
            certificateEntity.setIssuingAuthority( certificateRequest.getIssuingAuthority() );
        }

        if( StringUtils.isNotEmpty( certificateRequest.getCompanyBranding() ) ) {
            certificateEntity.setCompanyBranding( certificateRequest.getCompanyBranding() );
        }

        if( StringUtils.isNotEmpty( certificateRequest.getName() ) ) {

            Optional<CertificateEntity> update = certificateRepository.findByNameIgnoreCase(
                    certificateRequest.getName() );

            if( update.isPresent() ) {
                log.error( "Certificate with name " + certificateRequest.getName() + " already exists." );
                throw new InvalidInputException(
                        "Certificate with name " + certificateRequest.getName() + " already exists." );
            }

            certificateEntity.setName( certificateRequest.getName() );

        }

        if( StringUtils.isEmpty( certificateEntity.getCertificationCriteria() ) || ( StringUtils.isNotEmpty(
                certificateRequest.getCertificationCriteria() ) && !certificateRequest.getCertificationCriteria()
                .equals( certificateEntity.getCertificationCriteria() ) ) ) {
            certificateEntity.setCertificationCriteria( certificateRequest.getCertificationCriteria() );
        }

        if( StringUtils.isEmpty( certificateEntity.getRevokeCriteria() ) || ( StringUtils.isNotEmpty(
                certificateRequest.getRevokeCriteria() ) && !certificateRequest.getRevokeCriteria()
                .equals( certificateEntity.getRevokeCriteria() ) ) ) {
            certificateEntity.setRevokeCriteria( certificateRequest.getRevokeCriteria() );
        }

        if( StringUtils.isEmpty( certificateEntity.getCourseUrl() ) || ( StringUtils.isNotEmpty(
                certificateRequest.getCourseUrl() ) && !certificateRequest.getCourseUrl()
                .equals( certificateEntity.getCourseUrl() ) ) ) {
            certificateEntity.setCourseUrl( certificateRequest.getCourseUrl() );
        }

        if( StringUtils.isEmpty( certificateEntity.getWebsite() ) || ( StringUtils.isNotEmpty(
                certificateRequest.getWebsite() ) || !certificateRequest.getWebsite()
                .equals( certificateEntity.getWebsite() ) ) ) {
            certificateEntity.setWebsite( certificateRequest.getWebsite() );
        }

        try {
            return Converter.convertCertificateEntityToResponse( certificateRepository.save( certificateEntity ) );
        } catch( Exception e ) {
            log.error( "Failed to update Certificate Entity with id " + certificateId + "." );
            throw new AppException( "Failed to update Certificate Entity with id " + certificateId + "." );
        }

    }

    @Override
    public CertificateResponse findCertificateById( String certificateId ) throws InvalidInputException, EntityNotFoundException {

        if( StringUtils.isEmpty( certificateId ) ) {
            log.error( "Certificate Id cannot be null or empty." );
            throw new InvalidInputException( "Certificate Id cannot be null or empty." );
        }

        Optional<CertificateEntity> certificate = certificateRepository.findById( certificateId );

        if( certificate.isEmpty() ) {
            log.error( "Certificate with id " + certificateId + " was not found." );
            throw new EntityNotFoundException( "Certificate with id " + certificateId + " was not found." );
        }

        return Converter.convertCertificateEntityToResponse( certificate.get() );

    }

    @Override
    public List<CertificateResponse> findAllCertificatesByOrganization( String orgId ) throws InvalidInputException {

        if( StringUtils.isEmpty( orgId ) ) {
            log.error( "Organization Id cannot be null or empty." );
            throw new InvalidInputException( "Organization Id cannot be null or empty." );
        }

        List<CertificateEntity> certificates = certificateRepository.findAllByOrganization_id( orgId );

        if( certificates.isEmpty() ) {
            log.info( "No certificates found for given organization with id " + orgId + "." );
            return Collections.emptyList();
        }

        return certificates.stream().map( Converter::convertCertificateEntityToResponse ).toList();

    }

    @Override
    public String deleteCertificateById( String certificateId ) throws InvalidInputException, EntityNotFoundException, AppException {

        if( StringUtils.isEmpty( certificateId ) ) {
            log.error( "Certificate Id cannot be empty or null." );
            throw new InvalidInputException( "Certificate Id cannot be empty or null." );
        }

        Optional<CertificateEntity> certificate = certificateRepository.findById( certificateId );

        if( certificate.isEmpty() ) {
            log.error( "Certificate with id " + certificateId + " was not found." );
            throw new EntityNotFoundException( "Certificate with id " + certificateId + " was not found." );
        }

        try {
            certificateRepository.delete( certificate.get() );
        } catch( Exception e ) {
            log.error( "Failed to delete Certificate with id " + certificateId + "." );
            throw new AppException( "Failed to delete Certificate with id " + certificateId + "." );
        }

        return Constants.SUCCESS;

    }


    /***************************************************************
     ******************* Private Methods ***************************
     ***************************************************************/


}
