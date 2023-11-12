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
     * @param certifierRequest - Certifier Request Object.
     * @return certifierResponses - List of Certifier Response Objects.
     * @throws InvalidInputException   - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     * @throws AppException            - Incomplete conversions or repository operations.
     */
    List<CertifierResponse> certifyUsers(
            CertifierRequest certifierRequest ) throws InvalidInputException, EntityNotFoundException, AppException;


    /**
     * This service method retrieves a Certifier object by its id.
     * @param id - Certifier id.
     * @return certifierResponse - Certifier Response Object.
     * @throws InvalidInputException   - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     */
    CertifierResponse getCertifierById( String id ) throws InvalidInputException, EntityNotFoundException;


    /**
     * This service method retrieves a list of Certifier objects by Recipient Email.
     * @param email - Recipient Email id.
     * @return certifierResponses - List of Certifier Response Object.
     * @throws InvalidInputException - Invalid Inputs from User.
     */
    List<CertifierResponse> getCertifierByEmail( String email ) throws InvalidInputException;


    /**
     * This service method retrieves a list of Certifier objects by Certificate id.
     * @param certificateId - Certificate id.
     * @return certifierResponses - List of Certifier Response Object.
     * @throws InvalidInputException   - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     */
    List<CertifierResponse> getAllByCertificateId(
            String certificateId ) throws InvalidInputException, EntityNotFoundException;


    /**
     * This service method retrieves a list of Certifier objects by Organization Name.
     * @param organization - Organization name.
     * @return certifierResponses - List of Certifier Response Object.
     * @throws InvalidInputException   - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     */
    List<CertifierResponse> getAllByOraganization(
            String organization ) throws InvalidInputException, EntityNotFoundException;


    /**
     * This service method deletes a Certifier object by its id.
     * @param id - Certifier id.
     * @return Success - when object deleted.
     * @throws InvalidInputException   - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     * @throws AppException            - Incomplete conversions or repository operations.
     */
    String deleteCertifierById( String id ) throws InvalidInputException, EntityNotFoundException, AppException;


}
