package com.keystoneconstructs.credentia.service.implementation;

import com.keystoneconstructs.credentia.constant.Constants;
import com.keystoneconstructs.credentia.converters.Converter;
import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
import com.keystoneconstructs.credentia.exception.InvalidInputException;
import com.keystoneconstructs.credentia.model.CertifierRequest;
import com.keystoneconstructs.credentia.model.CertifierResponse;
import com.keystoneconstructs.credentia.pojo.CertificateEntity;
import com.keystoneconstructs.credentia.pojo.CertifierEntity;
import com.keystoneconstructs.credentia.repository.CertificateRepository;
import com.keystoneconstructs.credentia.repository.CertifierRepository;
import com.keystoneconstructs.credentia.service.CertifierService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
@Slf4j
public class CertifierServiceImpl implements CertifierService {

    @Autowired
    CertifierRepository certifierRepository;

    @Autowired
    CertificateRepository certificateRepository;


    @Override
    public List<CertifierResponse> certifyUsers(
            CertifierRequest certifierRequest ) throws InvalidInputException, EntityNotFoundException, AppException {

        if( certifierRequest == null || StringUtils.isEmpty( certifierRequest.getCertificateId() ) ||
                StringUtils.isEmpty( certifierRequest.getBatchNumber() ) ||
                StringUtils.isEmpty( certifierRequest.getRecipientOrganization() ) ||
                CollectionUtils.isEmpty( certifierRequest.getRecipientNames() ) ||
                CollectionUtils.isEmpty( certifierRequest.getRecipientEmails() ) ||
                certifierRequest.getTrainingStartDate() == null || certifierRequest.getTrainingEndDate() == null ||
                certifierRequest.getIssueDate() == null || certifierRequest.getExpirationDate() == null ||
                certifierRequest.getRecipientNames().size() != certifierRequest.getRecipientEmails().size() ) {

            log.error( "Certifier Request not valid. Please verify the Request Body." );
            throw new InvalidInputException( "Certifier Request not valid. Please verify the Request Body." );

        }

        Optional<CertificateEntity> certificate = certificateRepository.findById( certifierRequest.getCertificateId() );

        if( certificate.isEmpty() ) {
            log.error( "Certificate with id " + certifierRequest.getCertificateId() + " was not found." );
            throw new EntityNotFoundException(
                    "Certificate with id " + certifierRequest.getCertificateId() + " was not found." );
        }

        if( certifierRequest.getTrainingEndDate().before( certifierRequest.getTrainingStartDate() ) ) {
            log.error( "Invalid Certificate Start Date and Certificate End Date." );
            throw new InvalidInputException( "Invalid Certificate Start Date and Certificate End Date." );
        }

        if( certifierRequest.getIssueDate().before( certifierRequest.getTrainingEndDate() ) ) {
            log.error( "Invalid Certificate Issue Date." );
            throw new InvalidInputException( "Invalid Certificate Issue Date." );
        }

        if( certifierRequest.getExpirationDate().before( certifierRequest.getIssueDate() ) ) {
            log.error( "Invalid Certificate Expiration Date." );
            throw new InvalidInputException( "Invalid Certificate Expiration Date." );
        }

        List<String> recipientNames = certifierRequest.getRecipientNames();
        List<String> recipientEmails = certifierRequest.getRecipientEmails();

        List<CertifierEntity> certifierEntities = new ArrayList<>();

        for( int index = 0; index < recipientNames.size(); index++ ) {

            CertifierEntity certifierEntity = new CertifierEntity();
            certifierEntity.setId( UUID.randomUUID().toString() );
            certifierEntity.setCertificate( certificate.get() );
            certifierEntity.setBatchNumber( certifierRequest.getBatchNumber() );
            certifierEntity.setRecipientOrganization( certifierRequest.getRecipientOrganization() );
            certifierEntity.setTrainingStartDate( certifierRequest.getTrainingStartDate() );
            certifierEntity.setTrainingEndDate( certifierRequest.getTrainingEndDate() );
            certifierEntity.setIssueDate( certifierRequest.getIssueDate() );
            certifierEntity.setExpirationDate( certifierRequest.getExpirationDate() );
            certifierEntity.setRecipientName( recipientNames.get( index ) );
            certifierEntity.setRecipientEmail( recipientEmails.get( index ) );

            certifierEntities.add( certifierEntity );

        }

        try {

            certifierEntities = certifierRepository.saveAllAndFlush( certifierEntities );

        } catch( Exception e ) {

            log.error( "Failed to add certifiers." );
            throw new AppException( "Failed to add certifiers." );

        }

        if( certifierEntities.size() != recipientNames.size() ) {
            log.error( "Failed to add a few certificates." );
            throw new AppException( "Failed to add a few certificates." );
        }

        return certifierEntities.stream().map( Converter::convertCertifierEntityToResponse ).toList();

    }

    @Override
    public CertifierResponse getCertifierById( String id ) throws InvalidInputException, EntityNotFoundException {

        if( StringUtils.isEmpty( id ) ) {
            log.error( "Certifier Id cannot be null or empty." );
            throw new InvalidInputException( "Certifier Id cannot be null or empty." );
        }

        Optional<CertifierEntity> certifier = certifierRepository.findById( id );

        if( certifier.isEmpty() ) {
            log.error( "Certifier with id " + id + " was not found." );
            throw new EntityNotFoundException( "Certifier with id " + id + " was not found." );
        }

        return Converter.convertCertifierEntityToResponse( certifier.get() );

    }

    @Override
    public List<CertifierResponse> getCertifierByEmail( String email ) throws InvalidInputException {

        if( StringUtils.isEmpty( email ) ) {
            log.error( "Email id cannot be null or empty." );
            throw new InvalidInputException( "Email id cannot be null or empty." );
        }

        List<CertifierEntity> certifierEntities = certifierRepository.findByRecipientEmail( email );

        if( certifierEntities.isEmpty() ) {
            log.error( "No Certifiers with email " + email + " was not found." );
            return Collections.emptyList();
        }

        return certifierEntities.stream().map( Converter::convertCertifierEntityToResponse ).toList();

    }

    @Override
    public List<CertifierResponse> getAllByCertificateId(
            String certificateId ) throws InvalidInputException, EntityNotFoundException {

        if( StringUtils.isEmpty( certificateId ) ) {
            log.error( "Certifier Id cannot be null or empty." );
            throw new InvalidInputException( "Certifier Id cannot be null or empty." );
        }

        List<CertifierEntity> certifierEntities = certifierRepository.findAllByCertificate_id( certificateId );

        if( certifierEntities.isEmpty() ) {
            log.error( "Certifiers for Certificate id " + certificateId + " was not found." );
            throw new EntityNotFoundException( "Certifiers for Certificate id " + certificateId + " was not found." );
        }

        return certifierEntities.stream().map( Converter::convertCertifierEntityToResponse ).toList();

    }

    @Override
    public List<CertifierResponse> getAllByOraganization(
            String organization ) throws InvalidInputException, EntityNotFoundException {

        if( StringUtils.isEmpty( organization ) ) {
            log.error( "Organization name cannot be null or empty." );
            throw new InvalidInputException( "Organization name cannot be null or empty." );
        }

        List<CertifierEntity> certifierEntities = certifierRepository.findAllByRecipientOrganizationIgnoreCase(
                organization );

        if( certifierEntities.isEmpty() ) {
            log.error( "Certifiers for Organization name " + organization + " was not found." );
            throw new EntityNotFoundException( "Certifiers for Organization name " + organization + " was not found." );
        }

        return certifierEntities.stream().map( Converter::convertCertifierEntityToResponse ).toList();

    }

    @Override
    public String deleteCertifierById( String id ) throws InvalidInputException, EntityNotFoundException, AppException {

        if( StringUtils.isEmpty( id ) ) {
            log.error( "Certifier Id cannot be null or empty." );
            throw new InvalidInputException( "Certifier Id cannot be null or empty." );
        }

        Optional<CertifierEntity> certifier = certifierRepository.findById( id );

        if( certifier.isEmpty() ) {
            log.error( "Certifier with id " + id + " was not found." );
            throw new EntityNotFoundException( "Certifier with id " + id + " was not found." );
        }

        try {
            certifierRepository.delete( certifier.get() );
        } catch( Exception e ) {
            log.error( "Failed to delete Certifier with id " + id + "." );
            throw new AppException( "Failed to delete Certifier with id " + id + "." );
        }

        return Constants.SUCCESS;

    }


    /***************************************************************
     ******************* Private Methods ***************************
     ***************************************************************/


}
