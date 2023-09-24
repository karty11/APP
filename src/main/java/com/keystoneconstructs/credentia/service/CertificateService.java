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
     * @param certificateRequest
     * @return certificateResponse
     * @throws InvalidInputException
     * @throws AppException
     * @throws EntityNotFoundException
     */
    CertificateResponse addCertificate( CertificateRequest certificateRequest ) throws InvalidInputException, AppException, EntityNotFoundException;

    /**
     * This service method updates an existing Certificate with given id based on Certificate Request.
     * @param certificateId
     * @param certificateRequest
     * @return certificateResponse
     * @throws InvalidInputException
     * @throws EntityNotFoundException
     * @throws AppException
     */
    CertificateResponse updateCertificate( String certificateId, CertificateRequest certificateRequest ) throws InvalidInputException, EntityNotFoundException, AppException;

    /**
     * This service method retrieves a Certificate based on given id.
     * @param certificateId
     * @return certificateResponse
     * @throws InvalidInputException
     * @throws EntityNotFoundException
     */
    CertificateResponse findCertificateById( String certificateId ) throws InvalidInputException, EntityNotFoundException;

    /**
     * This service method retrieves a list of Certificates that belong to a given Organization id.
     * @param orgId
     * @return certificateResponses
     * @throws InvalidInputException
     */
    List<CertificateResponse> findAllCertificatesByOrganization(String orgId) throws InvalidInputException;

    /**
     * This service method deletes a Certificate based on a given id.
     * @param certificateId
     * @return Success
     * @throws InvalidInputException
     * @throws EntityNotFoundException
     * @throws AppException
     */
    String deleteCertificateById(String certificateId) throws InvalidInputException, EntityNotFoundException, AppException;

}
