package com.keystoneconstructs.credentia.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodeAndMessage {

    /***************************************************************
     ********************** Common Exceptions **********************
     ***************************************************************/

    EXCEPTION("CRDN0000", "Exception Message!"),
    INVALID_INPUT_EXCEPTION("CRDN0000", "Invalid request body or parameters. Please check all inputs."),


    /***************************************************************
     ****************** Organization Exceptions ********************
     ***************************************************************/

    FAILED_SAVE_ORGANIZATION("CRDN0000", "Failed to save Organization."),
    FAILED_DELETE_ORGANIZATION("CRDN0000", "Failed to delete Organization."),
    ORGANIZATION_ID_MISSING("CRDN0000", "Organization Id cannot be null or empty."),
    ORGANIZATION_ID_NOT_FOUND("CRDN0000", "Organization with given Id was not found."),
    DUPLICATE_ORGANIZATION_NAME("CRDN0000", "Organization with given Name already exists."),


    /***************************************************************
     ********************** User Exceptions ************************
     ***************************************************************/

    FAILED_SAVE_USER("CRDN0000", "Failed to save User."),
    FAILED_DELETE_USER("CRDN0000", "Failed to delete User."),
    USER_ID_MISSING("CRDN0000", "User Id cannot be null or empty."),
    USER_ID_NOT_FOUND("CRDN0000", "User with given Id was not found."),
    DUPLICATE_USER_EMAIL("CRDN0000", "User with given Email already exists."),
    USER_EMAIL_MISSING("CRDN0000", "User Email cannot be null or empty."),
    USER_EMAIL_NOT_FOUND("CRDN0000", "User with given Email Id was not found."),
    USER_GROUP_EMPTY("CRDN0000", "Users not found for given User Group Id."),
    USER_ORG_EMPTY("CRDN0000", "Users not found for given Organization Id."),


    /***************************************************************
     ******************* Certificate Exceptions ********************
     ***************************************************************/

    FAILED_SAVE_CERTIFICATE("CRDN0000", "Failed to save Certificate."),
    FAILED_DELETE_CERTIFICATE("CRDN0000", "Failed to delete Certificate."),
    CERTIFICATE_ID_MISSING("CRDN0000", "Certificate Id cannot be null or empty."),
    CERTIFICATE_ID_NOT_FOUND("CRDN0000", "Certificate with given Id was not found."),
    DUPLICATE_CERTIFICATE_NAME("CRDN0000", "Certificate with given Name already exists."),
    CERTIFICATE_ORG_EMPTY("CRDN0000", "Certificates not found for given Organization Id."),


    /***************************************************************
     ******************* Certifier Exceptions **********************
     ***************************************************************/

    FAILED_SAVE_CERTIFIER("CRDN0000", "Failed to save Certifier."),
    FAILED_DELETE_CERTIFIER("CRDN0000", "Failed to delete Certifier."),
    CERTIFIER_ID_MISSING("CRDN0000", "Certifier Id cannot be null or empty."),
    CERTIFIER_ID_NOT_FOUND("CRDN0000", "Certifier with given Id was not found."),
    CERTIFICATE_DATE_NOT_VALID("CRDN0000", "Dates input for certificate are not valid."),
    CERTIFIER_MAIL_NOT_FOUND("CRDN0000", "Certifier with given Email was not found."),
    CERTIFIER_ORG_NOT_FOUND("CRDN0000", "Certifier with given Organization was not found."),

    ;

    private final String errorCode;

    private final String message;


}
