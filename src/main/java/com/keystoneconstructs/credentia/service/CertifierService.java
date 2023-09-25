package com.keystoneconstructs.credentia.service;

import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
import com.keystoneconstructs.credentia.exception.InvalidInputException;
import com.keystoneconstructs.credentia.model.CertifierRequest;
import com.keystoneconstructs.credentia.model.CertifierResponse;

import java.util.List;

public interface CertifierService {

    /**
     * This service method certify a set of recipients as per Certifier Request.
     * @param certifierRequest
     * @return certifierResponses
     * @throws InvalidInputException
     * @throws EntityNotFoundException
     * @throws AppException
     */
    List<CertifierResponse> certifyUsers(
            CertifierRequest certifierRequest ) throws InvalidInputException, EntityNotFoundException, AppException;


    /**
     * This service method retrieves a Certifier object by its id.
     * @param id
     * @return certifierResponse
     * @throws InvalidInputException
     * @throws EntityNotFoundException
     */
    CertifierResponse getCertifierById( String id ) throws InvalidInputException, EntityNotFoundException;


    /**
     * This service method retrieves a list of Certifier objects by Recipient Email.
     * @param email
     * @return certifierResponses
     * @throws InvalidInputException
     */
    List<CertifierResponse> getCertifierByEmail( String email ) throws InvalidInputException;


    /**
     * This service method retrieves a list of Certifier objects by Certificate id.
     * @param certificateId
     * @return certifierResponses
     * @throws InvalidInputException
     * @throws EntityNotFoundException
     */
    List<CertifierResponse> getAllByCertificateId(
            String certificateId ) throws InvalidInputException, EntityNotFoundException;


    /**
     * This service method retrieves a list of Certifier objects by Organization Name.
     * @param organization
     * @return certifierResponses
     * @throws InvalidInputException
     * @throws EntityNotFoundException
     */
    List<CertifierResponse> getAllByOraganization(
            String organization ) throws InvalidInputException, EntityNotFoundException;


    /**
     * This service method deletes a Certifier object by its id.
     * @param id
     * @return Success
     * @throws InvalidInputException
     * @throws EntityNotFoundException
     * @throws AppException
     */
    String deleteCertifierById( String id ) throws InvalidInputException, EntityNotFoundException, AppException;


}
