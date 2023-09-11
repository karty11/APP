package com.keystoneconstructs.credentia.model;

import lombok.Data;

@Data
public class UserResponse {

    private String id;

    private String firstName;

    private String lastName;

    private String intials;

    private String role;

    private Contact contact;

    private String email;

    private String UserGroupId;


}
