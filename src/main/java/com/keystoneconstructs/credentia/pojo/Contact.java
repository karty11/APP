package com.keystoneconstructs.credentia.pojo;

import jakarta.persistence.Column;

public class Contact {

    @Column(name = "phone_number")
    private long phoneNumer;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "location")
    private String location;

    @Column(name = "linkedIn")
    private String linkedIn;

    @Column(name = "official_email")
    private String officialEmail;

}
