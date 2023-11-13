package com.keystoneconstructs.credentia.model;

import lombok.Data;

import java.sql.Date;

@Data
public class CertifierResponse {

    private String id;

    private CertificateResponse certificate;

    private String batchNumber;

    private Date trainingStartDate;

    private Date trainingEndDate;

    private Date issueDate;

    private Date expirationDate;

    private String recipientName;

    private String recipientEmail;

    private String recipientOrganization;


}
