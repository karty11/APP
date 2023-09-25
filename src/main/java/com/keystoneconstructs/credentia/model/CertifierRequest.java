package com.keystoneconstructs.credentia.model;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class CertifierRequest {

    private String certificateId;

    private String batchNumber;

    private Date trainingStartDate;

    private Date trainingEndDate;

    private Date issueDate;

    private Date expirationDate;

    private List<String> recipientNames;

    private List<String> recipientEmails;

    private String recipientOrganization;

}
