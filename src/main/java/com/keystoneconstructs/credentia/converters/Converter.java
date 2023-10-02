package com.keystoneconstructs.credentia.converters;

import com.keystoneconstructs.credentia.model.*;
import com.keystoneconstructs.credentia.pojo.*;
import org.apache.commons.lang3.StringUtils;

public interface Converter {

    /**
     * This method converts Contact Object to Contact Entity.
     * @param contact - Contact Object.
     * @return contactEntity - Contact Entity Object.
     */
    static ContactEntity convertContactToEntity( Contact contact ) {

        ContactEntity contactEntity = new ContactEntity();

        if( contact.getPhoneNumber() > 0 ) {
            contactEntity.setPhoneNumer( contact.getPhoneNumber() );
        }

        if( StringUtils.isNotEmpty( contact.getLocation() ) ) {
            contactEntity.setLocation( contact.getLocation() );
        }

        if( StringUtils.isNotEmpty( contact.getOfficialEmail() ) ) {
            contactEntity.setOfficialEmail( contact.getOfficialEmail() );
        }

        if( StringUtils.isNotEmpty( contact.getLinkedIn() ) ) {
            contactEntity.setLinkedIn( contact.getLinkedIn() );
        }

        if( StringUtils.isNotEmpty( contact.getAddress1() ) ) {
            contactEntity.setAddress1( contact.getAddress1() );
        }

        if( StringUtils.isNotEmpty( contact.getAddress2() ) ) {
            contactEntity.setAddress2( contact.getAddress2() );
        }

        return contactEntity;

    }


    /**
     * This method converts Contact Entity to Contact object.
     * @param contactEntity - Contact Entity Object.
     * @return contact - Contact Object.
     */
    static Contact convertEntityToContact( ContactEntity contactEntity ) {

        Contact contact = new Contact();

        if( contactEntity.getPhoneNumer() > 0 ) {
            contact.setPhoneNumber( contactEntity.getPhoneNumer() );
        }

        if( StringUtils.isNotEmpty( contactEntity.getLocation() ) ) {
            contact.setLocation( contactEntity.getLocation() );
        }

        if( StringUtils.isNotEmpty( contactEntity.getOfficialEmail() ) ) {
            contact.setOfficialEmail( contactEntity.getOfficialEmail() );
        }

        if( StringUtils.isNotEmpty( contactEntity.getLinkedIn() ) ) {
            contact.setLinkedIn( contactEntity.getLinkedIn() );
        }

        if( StringUtils.isNotEmpty( contactEntity.getAddress1() ) ) {
            contact.setAddress1( contactEntity.getAddress1() );
        }

        if( StringUtils.isNotEmpty( contactEntity.getAddress2() ) ) {
            contact.setAddress2( contactEntity.getAddress2() );
        }

        return contact;

    }

    /**
     * This method converts a User Entity to User Response Object.
     * @param userEntity - User Entity Object.
     * @return userResponse - User Response Object.
     */
    static UserResponse convertUserEntityToResponse( UserEntity userEntity ) {

        UserResponse userResponse = new UserResponse();
        userResponse.setId( userEntity.getId() );
        userResponse.setFirstName( userEntity.getFirstName() );
        userResponse.setLastName( userEntity.getLastName() );
        userResponse.setRole( userEntity.getRole() );
        userResponse.setEmail( userEntity.getEmail() );
        userResponse.setOrganizationResponse( convertOrganizationEntityToResponse( userEntity.getOrganization() ) );

        if( StringUtils.isNotEmpty( userEntity.getInitials() ) ) {
            userResponse.setInitials( userEntity.getInitials() );
        }

        if( userEntity.getContactEntity() != null ) {
            userResponse.setContact( convertEntityToContact( userEntity.getContactEntity() ) );
        }

        if( StringUtils.isNotEmpty( userEntity.getUserGroupId() ) ) {
            userResponse.setUserGroupId( userEntity.getUserGroupId() );
        }

        return userResponse;

    }


    /**
     * This method converts an Organization Entity to Organization Response object.
     * @param organizationEntity - Organization Entity Object.
     * @return organizationResponse - Organization Response Object.
     */
    static OrganizationResponse convertOrganizationEntityToResponse( OrganizationEntity organizationEntity ) {

        OrganizationResponse organizationResponse = new OrganizationResponse();
        organizationResponse.setId( organizationEntity.getId() );
        organizationResponse.setName( organizationEntity.getName() );
        organizationResponse.setIndustry( organizationEntity.getIndustry() );
        organizationResponse.setOrgCode( organizationEntity.getOrgCode() );

        if( organizationEntity.getContact() != null ) {
            organizationResponse.setContact( convertEntityToContact( organizationEntity.getContact() ) );
        }

        return organizationResponse;

    }

    /**
     * This method converts a Certificate Entity to Certificate Response.
     * @param certificateEntity - Certificate Entity Object.
     * @return certificateResponse - Certificate Response Object.
     */
    static CertificateResponse convertCertificateEntityToResponse( CertificateEntity certificateEntity ) {

        CertificateResponse certificateResponse = new CertificateResponse();
        certificateResponse.setId( certificateEntity.getId() );
        certificateResponse.setName( certificateEntity.getName() );
        certificateResponse.setIssuingAuthority( certificateEntity.getIssuingAuthority() );
        certificateResponse.setCompanyBranding( certificateEntity.getCompanyBranding() );
        certificateResponse.setOrganizationResponse(
                convertOrganizationEntityToResponse( certificateEntity.getOrganization() ) );

        if( StringUtils.isNotEmpty( certificateEntity.getCertificationCriteria() ) ) {
            certificateResponse.setCertificationCriteria( certificateEntity.getCertificationCriteria() );
        }

        if( StringUtils.isNotEmpty( certificateEntity.getRevokeCriteria() ) ) {
            certificateResponse.setRevokeCriteria( certificateEntity.getRevokeCriteria() );
        }

        if( StringUtils.isNotEmpty( certificateEntity.getCourseUrl() ) ) {
            certificateResponse.setCourseUrl( certificateEntity.getCourseUrl() );
        }

        if( StringUtils.isNotEmpty( certificateEntity.getWebsite() ) ) {
            certificateResponse.setWebsite( certificateEntity.getWebsite() );
        }

        return certificateResponse;

    }

    /**
     * This method converts a Certifier Entity to Certifier Response type.
     * @param certifierEntity - Certifier Entity Object.
     * @return certifierResponse - Certifier Response Object.
     */
    static CertifierResponse convertCertifierEntityToResponse( CertifierEntity certifierEntity ) {

        CertifierResponse certifierResponse = new CertifierResponse();
        certifierResponse.setId( certifierEntity.getId() );
        certifierResponse.setCertificate( convertCertificateEntityToResponse( certifierEntity.getCertificate() ) );
        certifierResponse.setBatchNumber( certifierEntity.getBatchNumber() );
        certifierResponse.setTrainingStartDate( certifierEntity.getTrainingStartDate() );
        certifierResponse.setTrainingEndDate( certifierEntity.getTrainingEndDate() );
        certifierResponse.setIssueDate( certifierEntity.getIssueDate() );
        certifierResponse.setExpirationDate( certifierEntity.getExpirationDate() );
        certifierResponse.setRecipientName( certifierEntity.getRecipientName() );
        certifierResponse.setRecipientEmail( certifierEntity.getRecipientEmail() );
        certifierResponse.setRecipientOrganization( certifierEntity.getRecipientOrganization() );

        return certifierResponse;

    }


}
