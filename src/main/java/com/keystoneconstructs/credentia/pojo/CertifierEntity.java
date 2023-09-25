package com.keystoneconstructs.credentia.pojo;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.Date;

@Entity
@Table( name = "certifier_entity" )
public class CertifierEntity extends AuditFields {


    @Id
    private String id;

    @OneToOne
    @JoinColumn( name = "certificate_id", nullable = false )
    private CertificateEntity certificate;

    @Column( name = "batch_number" )
    private String batchNumber;

    @Column( name = "training_start_date" )
    private Date trainingStartDate;

    @Column( name = "training_end_date" )
    private Date trainingEndDate;

    @Column( name = "issue_date" )
    private Date issueDate;

    @Column( name = "expiration_date" )
    private Date expirationDate;

    @Column( name = "recipient_name" )
    private String recipientName;

    @Column( name = "recipient_email" )
    private String recipientEmail;

    @Column( name = "recipient_organization" )
    private String recipientOrganization;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public CertificateEntity getCertificate() {
        return certificate;
    }

    public void setCertificate( CertificateEntity certificate ) {
        this.certificate = certificate;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber( String batchNumber ) {
        this.batchNumber = batchNumber;
    }

    public Date getTrainingStartDate() {
        return trainingStartDate;
    }

    public void setTrainingStartDate( Date trainingStartDate ) {
        this.trainingStartDate = trainingStartDate;
    }

    public Date getTrainingEndDate() {
        return trainingEndDate;
    }

    public void setTrainingEndDate( Date trainingEndDate ) {
        this.trainingEndDate = trainingEndDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate( Date issueDate ) {
        this.issueDate = issueDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate( Date expirationDate ) {
        this.expirationDate = expirationDate;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName( String recipientName ) {
        this.recipientName = recipientName;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail( String recipientEmail ) {
        this.recipientEmail = recipientEmail;
    }

    public String getRecipientOrganization() {
        return recipientOrganization;
    }

    public void setRecipientOrganization( String recipientOrganization ) {
        this.recipientOrganization = recipientOrganization;
    }

    @Override public boolean equals( Object o ) {
        if( this == o ) return true;

        if( o == null || getClass() != o.getClass() ) return false;

        CertifierEntity that = ( CertifierEntity ) o;

        return new EqualsBuilder().append( id, that.id )
                .append( certificate, that.certificate ).append( batchNumber, that.batchNumber )
                .append( trainingStartDate, that.trainingStartDate ).append( trainingEndDate, that.trainingEndDate )
                .append( issueDate, that.issueDate ).append( expirationDate, that.expirationDate )
                .append( recipientName, that.recipientName ).append( recipientEmail, that.recipientEmail )
                .append( recipientOrganization, that.recipientOrganization ).isEquals();
    }

    @Override public int hashCode() {
        return new HashCodeBuilder( 17, 37 ).append( id ).append( certificate ).append( batchNumber )
                .append( trainingStartDate ).append( trainingEndDate ).append( issueDate ).append( expirationDate )
                .append( recipientName ).append( recipientEmail ).append( recipientOrganization ).toHashCode();
    }

    @Override public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", certificate=" + certificate +
                ", batchNumber='" + batchNumber + '\'' +
                ", trainingStartDate=" + trainingStartDate +
                ", trainingEndDate=" + trainingEndDate +
                ", issueDate=" + issueDate +
                ", expirationDate=" + expirationDate +
                ", recipientName='" + recipientName + '\'' +
                ", recipientEmail='" + recipientEmail + '\'' +
                ", recipientOrganization='" + recipientOrganization + '\'' +
                '}';
    }

}
