package com.keystoneconstructs.credentia.model;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CertificateRequest {

    private String issuingAuthority;

    private String companyBranding;

    private String name;

    private String certificationCriteria;

    private String revokeCriteria;

    private String courseUrl;

    private String website;

    private String organizationId;

}
