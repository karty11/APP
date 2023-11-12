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
     * @param organizationRequest - Organization Request Object.
     * @return organizationResponse - Organization Response Object.
     * @throws InvalidInputException - Invalid Inputs from User.
     * @throws AppException          - Incomplete conversions or repository operations.
     */
    OrganizationResponse createOrganization(
            OrganizationRequest organizationRequest ) throws InvalidInputException, AppException;

    /**
     * This service method updates an existing organization with the given Organization id using Organization Request.
     * @param organizationRequest - Organization Request Object.
     * @param organizationId      - Organization id.
     * @return organizationResponse - Organization Response Object.
     * @throws InvalidInputException   - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     * @throws AppException            - Incomplete conversions or repository operations.
     */
    OrganizationResponse updateOrganization( OrganizationRequest organizationRequest,
            String organizationId ) throws InvalidInputException, EntityNotFoundException, AppException;

    /**
     * This service method retrieves an existing Organization using an Organization id.
     * @param organizationId - Organization id.
     * @return organizationResponse - Organization Response Object.
     * @throws EntityNotFoundException - Required entity does not exists.
     * @throws InvalidInputException   - Invalid Inputs from User.
     */
    OrganizationResponse findOrganizationById(
            String organizationId ) throws EntityNotFoundException, InvalidInputException;

    /**
     * This service method retrieves a list of all Organizations.
     * @return organizationResponses - List of Organization Response Object.
     */
    List<OrganizationResponse> findAllOrganizations();

    /**
     * This service method deletes an existing Organization using an Organization id.
     * @param organizationId - Organization id.
     * @return Success - when object deleted.
     * @throws InvalidInputException   - Invalid Inputs from User.
     * @throws EntityNotFoundException - Required entity does not exists.
     * @throws AppException            - Incomplete conversions or repository operations.
     */
    String deleteOrganizationById(
            String organizationId ) throws InvalidInputException, EntityNotFoundException, AppException;

}
