package com.keystoneconstructs.credentia.service;

import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
import com.keystoneconstructs.credentia.exception.InvalidInputException;
import com.keystoneconstructs.credentia.model.CertificateRequest;
import com.keystoneconstructs.credentia.model.CertificateResponse;

import java.util.List;

public interface CertificateService {

    /**
     * This service method creates a new Certificate based on Certificate Request.
     * @param certificateRequest - Certificate Request Object.
     * @return certificateResponse - Certificate Response Object.
     * @throws InvalidInputException - Invalid Inputs from User.
     * @throws AppException - Incomplete conversions or repository operations.
     * @throws EntityNotFoundException - Required entity does not exists.
     */
    CertificateResponse addCertificate( CertificateRequest certificateRequest ) throws InvalidInputException, AppException, EntityNotFoundException;

    /**
     * This service method updates an existing Certificate with given id based on Certificate Request.
     * @param certificateId - Certificate id.
     * @param certificateRequest - Certificate Request Object.
     * @return certificateResponse - Certificate Response Object.
     * @throws InvalidInputException - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     * @throws AppException - Incomplete conversions or repository operations.
     */
    CertificateResponse updateCertificate( String certificateId, CertificateRequest certificateRequest ) throws InvalidInputException, EntityNotFoundException, AppException;

    /**
     * This service method retrieves a Certificate based on given id.
     * @param certificateId - Certificate id.
     * @return certificateResponse - Certificate Response Object.
     * @throws InvalidInputException - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     */
    CertificateResponse findCertificateById( String certificateId ) throws InvalidInputException, EntityNotFoundException;

    /**
     * This service method retrieves a list of Certificates that belong to a given Organization id.
     * @param orgId - Organization id.
     * @return certificateResponses - List of Certificate Response Object.
     * @throws InvalidInputException - Invalid Inputs from User.
     */
    List<CertificateResponse> findAllCertificatesByOrganization(String orgId) throws InvalidInputException;

    /**
     * This service method deletes a Certificate based on a given id.
     * @param certificateId - Certificate id.
     * @return Success - when object deleted.
     * @throws InvalidInputException - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     * @throws AppException - Incomplete conversions or repository operations.
     */
    String deleteCertificateById(String certificateId) throws InvalidInputException, EntityNotFoundException, AppException;

}
