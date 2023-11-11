package com.keystoneconstructs.credentia.service.implementation;

import com.keystoneconstructs.credentia.constant.Constants;
import com.keystoneconstructs.credentia.converters.Converter;
import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
import com.keystoneconstructs.credentia.exception.ErrorCodeAndMessage;
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

            log.error( ErrorCodeAndMessage.INVALID_INPUT_EXCEPTION.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.INVALID_INPUT_EXCEPTION );

        }

        Optional<CertificateEntity> certificate = certificateRepository.findById( certifierRequest.getCertificateId() );

        if( certificate.isEmpty() ) {
            log.error( ErrorCodeAndMessage.CERTIFICATE_ID_NOT_FOUND.getMessage() + "\n" + Constants.ID + " : " +
                    certifierRequest.getCertificateId() );
            throw new EntityNotFoundException( ErrorCodeAndMessage.CERTIFICATE_ID_NOT_FOUND );
        }

        if( certifierRequest.getTrainingEndDate().before( certifierRequest.getTrainingStartDate() ) ) {
            log.error( ErrorCodeAndMessage.CERTIFICATE_DATE_NOT_VALID.getMessage() + "\nStart Date before End Date." );
            throw new InvalidInputException( ErrorCodeAndMessage.CERTIFICATE_DATE_NOT_VALID );
        }

        if( certifierRequest.getIssueDate().before( certifierRequest.getTrainingEndDate() ) ) {
            log.error( ErrorCodeAndMessage.CERTIFICATE_DATE_NOT_VALID.getMessage() + "\nIssue Date." );
            throw new InvalidInputException( ErrorCodeAndMessage.CERTIFICATE_DATE_NOT_VALID );
        }

        if( certifierRequest.getExpirationDate().before( certifierRequest.getIssueDate() ) ) {
            log.error( ErrorCodeAndMessage.CERTIFICATE_DATE_NOT_VALID.getMessage() + "\nExpiration Date." );
            throw new InvalidInputException( ErrorCodeAndMessage.CERTIFICATE_DATE_NOT_VALID );
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

            log.error( ErrorCodeAndMessage.FAILED_SAVE_CERTIFIER.getMessage() );
            throw new AppException( ErrorCodeAndMessage.FAILED_SAVE_CERTIFIER );

        }

        if( certifierEntities.size() != recipientNames.size() ) {
            log.error( ErrorCodeAndMessage.FAILED_SAVE_CERTIFIER.getMessage() );
            throw new AppException( ErrorCodeAndMessage.FAILED_SAVE_CERTIFIER );
        }

        return certifierEntities.stream().map( Converter::convertCertifierEntityToResponse ).toList();

    }

    @Override
    public CertifierResponse getCertifierById( String id ) throws InvalidInputException, EntityNotFoundException {

        if( StringUtils.isEmpty( id ) ) {
            log.error( ErrorCodeAndMessage.CERTIFIER_ID_MISSING.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.CERTIFIER_ID_MISSING );
        }

        Optional<CertifierEntity> certifier = certifierRepository.findById( id );

        if( certifier.isEmpty() ) {
            log.error( ErrorCodeAndMessage.CERTIFIER_ID_NOT_FOUND.getMessage() + "\n" + Constants.ID + " : " + id );
            throw new EntityNotFoundException( ErrorCodeAndMessage.CERTIFIER_ID_NOT_FOUND );
        }

        return Converter.convertCertifierEntityToResponse( certifier.get() );

    }

    @Override
    public List<CertifierResponse> getCertifierByEmail( String email ) throws InvalidInputException {

        if( StringUtils.isEmpty( email ) ) {
            log.error( ErrorCodeAndMessage.USER_EMAIL_MISSING.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.USER_EMAIL_MISSING );
        }

        List<CertifierEntity> certifierEntities = certifierRepository.findByRecipientEmail( email );

        if( certifierEntities.isEmpty() ) {
            log.error( ErrorCodeAndMessage.CERTIFIER_MAIL_NOT_FOUND.getMessage() + "\n" + Constants.EMAIL + " : " +
                    email );
            return Collections.emptyList();
        }

        return certifierEntities.stream().map( Converter::convertCertifierEntityToResponse ).toList();

    }

    @Override
    public List<CertifierResponse> getAllByCertificateId(
            String certificateId ) throws InvalidInputException, EntityNotFoundException {

        if( StringUtils.isEmpty( certificateId ) ) {
            log.error( ErrorCodeAndMessage.CERTIFICATE_ID_MISSING.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.CERTIFICATE_ID_MISSING );
        }

        List<CertifierEntity> certifierEntities = certifierRepository.findAllByCertificate_id( certificateId );

        if( certifierEntities.isEmpty() ) {
            log.error( ErrorCodeAndMessage.CERTIFICATE_ID_NOT_FOUND.getMessage() + "\n" + Constants.ID + " : " +
                    certificateId );
            return Collections.emptyList();
        }

        return certifierEntities.stream().map( Converter::convertCertifierEntityToResponse ).toList();

    }

    @Override
    public List<CertifierResponse> getAllByOraganization(
            String organization ) throws InvalidInputException, EntityNotFoundException {

        if( StringUtils.isEmpty( organization ) ) {
            log.error( ErrorCodeAndMessage.ORGANIZATION_ID_MISSING.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.ORGANIZATION_ID_MISSING );
        }

        List<CertifierEntity> certifierEntities = certifierRepository.findAllByRecipientOrganizationIgnoreCase(
                organization );

        if( certifierEntities.isEmpty() ) {
            log.error( ErrorCodeAndMessage.CERTIFIER_ORG_NOT_FOUND.getMessage() + "\n" + Constants.ORG_ID + " : " +
                    organization );
            return Collections.emptyList();
        }

        return certifierEntities.stream().map( Converter::convertCertifierEntityToResponse ).toList();

    }

    @Override
    public String deleteCertifierById( String id ) throws InvalidInputException, EntityNotFoundException, AppException {

        if( StringUtils.isEmpty( id ) ) {
            log.error( ErrorCodeAndMessage.CERTIFIER_ID_MISSING.getMessage() );
            throw new InvalidInputException( ErrorCodeAndMessage.CERTIFIER_ID_MISSING );
        }

        Optional<CertifierEntity> certifier = certifierRepository.findById( id );

        if( certifier.isEmpty() ) {
            log.error( ErrorCodeAndMessage.CERTIFIER_ID_NOT_FOUND + "\n" + Constants.ID + " : " + id );
            throw new EntityNotFoundException( ErrorCodeAndMessage.CERTIFIER_ID_NOT_FOUND );
        }

        try {
            certifierRepository.delete( certifier.get() );
        } catch( Exception e ) {
            log.error( ErrorCodeAndMessage.FAILED_DELETE_CERTIFIER.getMessage() + "\n" + Constants.ID + " : " + id );
            throw new AppException( ErrorCodeAndMessage.FAILED_DELETE_CERTIFIER );
        }

        return Constants.SUCCESS;

    }


    /*--------------------------------------------------------
     ------------------ Private Methods ----------------------
     --------------------------------------------------------*/

    private String getString(String string){
        return string;
    }

}
