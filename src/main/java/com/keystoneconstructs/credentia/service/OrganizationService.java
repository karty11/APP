package com.keystoneconstructs.credentia.service;

import com.keystoneconstructs.credentia.exception.AppException;
import com.keystoneconstructs.credentia.exception.EntityNotFoundException;
import com.keystoneconstructs.credentia.exception.InvalidInputException;
import com.keystoneconstructs.credentia.model.OrganizationRequest;
import com.keystoneconstructs.credentia.model.OrganizationResponse;

import java.util.List;

public interface OrganizationService {

    /**
     * This service method creates a new Organization based on the Organization Request.
     * @param organizationRequest
     * @return organizationResponse
     * @throws InvalidInputException
     * @throws AppException
     */
    OrganizationResponse createOrgainzation( OrganizationRequest organizationRequest ) throws InvalidInputException, AppException;

    /**
     *This service method updates an existing organization with the given Organization Id using Organization Request.
     * @param organizationRequest
     * @param organizationId
     * @return organizationResponse
     * @throws InvalidInputException
     * @throws EntityNotFoundException
     * @throws AppException
     */
    OrganizationResponse updateOrganization( OrganizationRequest organizationRequest, String organizationId ) throws InvalidInputException, EntityNotFoundException, AppException;

    /**
     * This service method retrieves an existing Organization using an Organization Id.
     * @param organizationId
     * @return organizationResponse
     * @throws EntityNotFoundException
     * @throws InvalidInputException
     */
    OrganizationResponse findOrganizationById( String organizationId ) throws EntityNotFoundException, InvalidInputException;

    /**
     * This service method retrieves a list of all Organizations.
     * @return organizationResponses
     */
    List<OrganizationResponse> findAllOrganizations();

    /**
     * This service method deletes an existing Organization using an Organization Id.
     * @param organizationId
     * @return Success
     * @throws InvalidInputException
     * @throws EntityNotFoundException
     * @throws AppException
     */
    String deleteOrganizationById( String organizationId ) throws InvalidInputException, EntityNotFoundException, AppException;

}
