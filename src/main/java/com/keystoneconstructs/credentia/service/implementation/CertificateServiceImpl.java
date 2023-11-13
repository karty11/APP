package com.keystoneconstructs.credentia.service.implementation;

import com.keystoneconstructs.credentia.constant.Constants;
import com.keystoneconstructs.credentia.converters.Converter;
import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
import com.keystoneconstructs.credentia.exception.ErrorCodeAndMessage;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public CertificateResponse addCertificate( CertificateRequest certificateRequest, String userId )
            throws InvalidInputException, AppException, EntityNotFoundException {

        if( certificateRequest == null || StringUtils.isEmpty( certificateRequest.getIssuingAuthority() ) ||
                StringUtils.isEmpty( certificateRequest.getCompanyBranding() ) ||
                StringUtils.isEmpty( certificateRequest.getName() ) ||
                StringUtils.isEmpty( certificateRequest.getOrganizationId() ) ) {
            log.error( ErrorCodeAndMessage.INVALID_INPUT_EXCEPTION.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.INVALID_INPUT_EXCEPTION );
        }

        Optional<CertificateEntity> certificate = certificateRepository.findByNameIgnoreCase(
                certificateRequest.getName() );

        if( certificate.isPresent() ) {
            log.error( ErrorCodeAndMessage.DUPLICATE_CERTIFICATE_NAME.getMessage() + "\n" + Constants.NAME + " : " +
                    certificateRequest.getName() );
            throw new InvalidInputException( ErrorCodeAndMessage.DUPLICATE_CERTIFICATE_NAME );
        }

        Optional<OrganizationEntity> organization = organizationRepository.findById(
                certificateRequest.getOrganizationId() );

        if( organization.isEmpty() ) {
            log.error( ErrorCodeAndMessage.ORGANIZATION_ID_NOT_FOUND.getMessage() + "\n" + Constants.ORG_ID + " : " +
                    certificateRequest.getOrganizationId() );
            throw new EntityNotFoundException( ErrorCodeAndMessage.ORGANIZATION_ID_NOT_FOUND );
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

        certificateEntity.setCreatedBy( userId );
        certificateEntity.setCreatedOn( LocalDateTime.now() );
        certificateEntity.setUpdatedBy( userId );
        certificateEntity.setUpdatedOn( LocalDateTime.now() );

        try {
            return Converter.convertCertificateEntityToResponse( certificateRepository.save( certificateEntity ) );
        } catch( Exception e ) {
            log.error( ErrorCodeAndMessage.FAILED_SAVE_CERTIFICATE.getMessage() );
            throw new AppException( ErrorCodeAndMessage.FAILED_SAVE_CERTIFICATE );
        }

    }


    @Override
    public CertificateResponse updateCertificate( String certificateId, CertificateRequest certificateRequest,
            String userId ) throws InvalidInputException, EntityNotFoundException, AppException {

        if( StringUtils.isEmpty( certificateId ) || certificateRequest == null ) {
            log.error( ErrorCodeAndMessage.INVALID_INPUT_EXCEPTION.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.INVALID_INPUT_EXCEPTION );
        }

        Optional<CertificateEntity> certificate = certificateRepository.findById( certificateId );

        if( certificate.isEmpty() ) {
            log.error( ErrorCodeAndMessage.CERTIFICATE_ID_NOT_FOUND.getMessage() + "\n" + Constants.ID + " : " +
                    certificateId );
            throw new EntityNotFoundException( ErrorCodeAndMessage.CERTIFICATE_ID_NOT_FOUND );
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
                log.error( ErrorCodeAndMessage.DUPLICATE_CERTIFICATE_NAME.getMessage() + "\n" + Constants.NAME + " : " +
                        certificateRequest.getName() );
                throw new InvalidInputException( ErrorCodeAndMessage.DUPLICATE_CERTIFICATE_NAME );
            }

            certificateEntity.setName( certificateRequest.getName() );

        }

        if( StringUtils.isEmpty( certificateEntity.getCertificationCriteria() ) ||
                ( StringUtils.isNotEmpty( certificateRequest.getCertificationCriteria() ) &&
                        !certificateRequest.getCertificationCriteria()
                                .equals( certificateEntity.getCertificationCriteria() ) ) ) {
            certificateEntity.setCertificationCriteria( certificateRequest.getCertificationCriteria() );
        }

        if( StringUtils.isEmpty( certificateEntity.getRevokeCriteria() ) ||
                ( StringUtils.isNotEmpty( certificateRequest.getRevokeCriteria() ) &&
                        !certificateRequest.getRevokeCriteria().equals( certificateEntity.getRevokeCriteria() ) ) ) {
            certificateEntity.setRevokeCriteria( certificateRequest.getRevokeCriteria() );
        }

        if( StringUtils.isEmpty( certificateEntity.getCourseUrl() ) ||
                ( StringUtils.isNotEmpty( certificateRequest.getCourseUrl() ) &&
                        !certificateRequest.getCourseUrl().equals( certificateEntity.getCourseUrl() ) ) ) {
            certificateEntity.setCourseUrl( certificateRequest.getCourseUrl() );
        }

        if( StringUtils.isEmpty( certificateEntity.getWebsite() ) ||
                ( StringUtils.isNotEmpty( certificateRequest.getWebsite() ) ||
                        !certificateRequest.getWebsite().equals( certificateEntity.getWebsite() ) ) ) {
            certificateEntity.setWebsite( certificateRequest.getWebsite() );
        }

        certificateEntity.setUpdatedBy( userId );
        certificateEntity.setUpdatedOn( LocalDateTime.now() );

        try {
            return Converter.convertCertificateEntityToResponse( certificateRepository.save( certificateEntity ) );
        } catch( Exception e ) {
            log.error( ErrorCodeAndMessage.FAILED_SAVE_CERTIFICATE.getMessage() + "\n" + Constants.ID + " : " +
                    certificateId );
            throw new AppException( ErrorCodeAndMessage.FAILED_SAVE_CERTIFICATE );
        }

    }


    @Override
    public CertificateResponse findCertificateById( String certificateId )
            throws InvalidInputException, EntityNotFoundException {

        if( StringUtils.isEmpty( certificateId ) ) {
            log.error( ErrorCodeAndMessage.CERTIFIER_ID_MISSING.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.CERTIFIER_ID_MISSING );
        }

        Optional<CertificateEntity> certificate = certificateRepository.findById( certificateId );

        if( certificate.isEmpty() ) {
            log.error( ErrorCodeAndMessage.CERTIFICATE_ID_NOT_FOUND.getMessage() + "\n" + Constants.ID + " : " +
                    certificateId );
            throw new EntityNotFoundException( ErrorCodeAndMessage.CERTIFIER_ID_NOT_FOUND );
        }

        return Converter.convertCertificateEntityToResponse( certificate.get() );

    }


    @Override
    public List<CertificateResponse> findAllCertificatesByOrganization( String orgId ) throws InvalidInputException {

        if( StringUtils.isEmpty( orgId ) ) {
            log.error( ErrorCodeAndMessage.ORGANIZATION_ID_MISSING.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.ORGANIZATION_ID_MISSING );
        }

        List<CertificateEntity> certificates = certificateRepository.findAllByOrganization_id( orgId );

        if( certificates.isEmpty() ) {
            log.info(
                    ErrorCodeAndMessage.CERTIFICATE_ORG_EMPTY.getMessage() + "\n" + Constants.ORG_ID + " : " + orgId );
            return Collections.emptyList();
        }

        return certificates.stream().map( Converter::convertCertificateEntityToResponse ).toList();

    }


    @Override
    public String deleteCertificateById( String certificateId )
            throws InvalidInputException, EntityNotFoundException, AppException {

        if( StringUtils.isEmpty( certificateId ) ) {
            log.error( ErrorCodeAndMessage.CERTIFICATE_ID_MISSING.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.CERTIFICATE_ID_MISSING );
        }

        Optional<CertificateEntity> certificate = certificateRepository.findById( certificateId );

        if( certificate.isEmpty() ) {
            log.error( ErrorCodeAndMessage.CERTIFICATE_ID_NOT_FOUND.getMessage() + "\n" + Constants.ID + " : " +
                    certificateId );
            throw new EntityNotFoundException( ErrorCodeAndMessage.CERTIFICATE_ID_NOT_FOUND );
        }

        try {
            certificateRepository.delete( certificate.get() );
        } catch( Exception e ) {
            log.error( ErrorCodeAndMessage.FAILED_DELETE_CERTIFICATE.getMessage() + "\n" + Constants.ID + " : " +
                    certificateId );
            throw new AppException( ErrorCodeAndMessage.FAILED_DELETE_CERTIFICATE );
        }

        return Constants.SUCCESS;

    }


    /*--------------------------------------------------------
     ------------------ Private Methods ----------------------
     --------------------------------------------------------*/


    private String getString( String string ) {

        return string;
    }

}
