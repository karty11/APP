package com.keystoneconstructs.credentia.pojo;

import jakarta.persistence.Column;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    public Contact() {
        //Empty Constructor
    }

    public long getPhoneNumer() {
        return phoneNumer;
    }

    public void setPhoneNumer(long phoneNumer) {
        this.phoneNumer = phoneNumer;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getOfficialEmail() {
        return officialEmail;
    }

    public void setOfficialEmail(String officialEmail) {
        this.officialEmail = officialEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return new EqualsBuilder().append(phoneNumer, contact.phoneNumer).append(address1, contact.address1).append(address2, contact.address2).append(location, contact.location).append(linkedIn, contact.linkedIn).append(officialEmail, contact.officialEmail).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(phoneNumer).append(address1).append(address2).append(location).append(linkedIn).append(officialEmail).toHashCode();
    }

    @Override
    public String toString() {
        return "{" +
                "phoneNumer=" + phoneNumer +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", location='" + location + '\'' +
                ", linkedIn='" + linkedIn + '\'' +
                ", officialEmail='" + officialEmail + '\'' +
                '}';
    }

}
