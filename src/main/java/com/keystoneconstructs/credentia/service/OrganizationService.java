package com.keystoneconstructs.credentia.service;

import com.keystoneconstructs.credentia.model.OrganizationRequest;
import com.keystoneconstructs.credentia.model.OrganizationResponse;

import java.util.List;

public interface OrganizationService {

    OrganizationResponse createOrgainzation( OrganizationRequest organizationRequest );

    OrganizationResponse updateOrganization( OrganizationRequest organizationRequest, String organizationId );

    OrganizationResponse findOrganizationById( String organizationId );

    List<OrganizationResponse> findAllOrganizations();

    String deleteOrganizationById( String organizationId );

}
