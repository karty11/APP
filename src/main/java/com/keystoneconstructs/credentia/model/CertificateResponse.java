package com.keystoneconstructs.credentia.model;

import lombok.Data;

@Data
public class CertificateResponse {

    private String id;

    private String issuingAuthority;

    private String companyBranding;

    private String name;

    private String certificationCriteria;

    private String revokeCriteria;

    private String courseUrl;

    private String website;

    private OrganizationResponse organizationResponse;


}
