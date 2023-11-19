package com.keystoneconstructs.credentia.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodeAndMessage {

    /***************************************************************
     ********************** Common Exceptions **********************
     ***************************************************************/

    INVALID_INPUT_EXCEPTION( "CRDN0001", "Invalid request body or parameters. Please check all inputs." ),


    /***************************************************************
     ****************** Organization Exceptions ********************
     ***************************************************************/

    FAILED_SAVE_ORGANIZATION( "CRDN0011", "Failed to save Organization." ),
    FAILED_DELETE_ORGANIZATION( "CRDN0012", "Failed to delete Organization." ),
    ORGANIZATION_ID_MISSING( "CRDN0013", "Organization Id cannot be null or empty." ),
    ORGANIZATION_ID_NOT_FOUND( "CRDN0014", "Organization with given Id was not found." ),
    DUPLICATE_ORGANIZATION_NAME( "CRDN00015", "Organization with given Name already exists." ),


    /***************************************************************
     ********************** User Exceptions ************************
     ***************************************************************/

    FAILED_SAVE_USER( "CRDN0031", "Failed to save User." ),
    FAILED_DELETE_USER( "CRDN0032", "Failed to delete User." ),
    USER_ID_MISSING( "CRDN0033", "User Id cannot be null or empty." ),
    USER_ID_NOT_FOUND( "CRDN0034", "User with given Id was not found." ),
    DUPLICATE_USER_EMAIL( "CRDN0035", "User with given Email already exists." ),
    USER_EMAIL_MISSING( "CRDN0036", "User Email cannot be null or empty." ),
    USER_EMAIL_NOT_FOUND( "CRDN0037", "User with given Email Id was not found." ),
    USER_GROUP_EMPTY( "CRDN0038", "Users not found for given User Group Id." ),
    USER_ORG_EMPTY( "CRDN00039", "Users not found for given Organization Id." ),
    FAILED_ENCRYPT_PASSWORD( "CRDN0040", "Failed to encrypt provided Password." ),
    INVALID_PASSWORD( "CRDN00041", "Password provided is invalid." ),
    USER_EMAIL_PASSWORD_MISSING( "CRDN0042", "User Email or Password cannot be null or empty." ),


    /***************************************************************
     ******************* Certificate Exceptions ********************
     ***************************************************************/

    FAILED_SAVE_CERTIFICATE( "CRDN0051", "Failed to save Certificate." ),
    FAILED_DELETE_CERTIFICATE( "CRDN0052", "Failed to delete Certificate." ),
    CERTIFICATE_ID_MISSING( "CRDN0053", "Certificate Id cannot be null or empty." ),
    CERTIFICATE_ID_NOT_FOUND( "CRDN0054", "Certificate with given Id was not found." ),
    DUPLICATE_CERTIFICATE_NAME( "CRDN0055", "Certificate with given Name already exists." ),
    CERTIFICATE_ORG_EMPTY( "CRDN0056", "Certificates not found for given Organization Id." ),


    /***************************************************************
     ******************* Certifier Exceptions **********************
     ***************************************************************/

    FAILED_SAVE_CERTIFIER( "CRDN0071", "Failed to save Certifier." ),
    FAILED_DELETE_CERTIFIER( "CRDN0072", "Failed to delete Certifier." ),
    CERTIFIER_ID_MISSING( "CRDN0073", "Certifier Id cannot be null or empty." ),
    CERTIFIER_ID_NOT_FOUND( "CRDN0074", "Certifier with given Id was not found." ),
    CERTIFICATE_DATE_NOT_VALID( "CRDN0075", "Dates input for certificate are not valid." ),
    CERTIFIER_MAIL_NOT_FOUND( "CRDN0076", "Certifier with given Email was not found." ),
    CERTIFIER_ORG_NOT_FOUND( "CRDN0077", "Certifier with given Organization was not found." ),

    ;

    private final String errorCode;

    private final String message;


}
