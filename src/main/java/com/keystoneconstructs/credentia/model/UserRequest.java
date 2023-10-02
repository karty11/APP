package com.keystoneconstructs.credentia.model;

import lombok.Data;

@Data
public class UserRequest {

    private String firstName;

    private String lastName;

    private String initials;

    private String role;

    private String email;

    private Contact contact;

    private String organizationId;

    private UserOrganizationRequest organizationRequest;

    private String password;

}
