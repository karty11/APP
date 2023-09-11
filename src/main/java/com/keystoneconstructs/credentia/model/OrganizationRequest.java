package com.keystoneconstructs.credentia.model;

import lombok.Data;

@Data
public class OrganizationRequest {

    private String name;

    private String orgCode;

    private String industry;

    private Contact contact;

}
