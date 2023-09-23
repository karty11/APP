package com.keystoneconstructs.credentia.model;

import lombok.Data;

@Data
public class OrganizationResponse {

    private String id;

    private String name;

    private String orgCode;

    private String industry;

    private Contact contact;

}
