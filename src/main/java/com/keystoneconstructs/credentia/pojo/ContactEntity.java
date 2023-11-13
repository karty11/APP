package com.keystoneconstructs.credentia.pojo;

import com.google.gson.annotations.Expose;
import jakarta.persistence.Column;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ContactEntity {

    @Expose
    @Column( name = "phone_number" )
    private long phoneNumer;

    @Expose
    @Column( name = "address1" )
    private String address1;

    @Expose
    @Column( name = "address2" )
    private String address2;

    @Expose
    @Column( name = "location" )
    private String location;

    @Expose
    @Column( name = "linkedIn" )
    private String linkedIn;

    @Expose
    @Column( name = "official_email" )
    private String officialEmail;


    public ContactEntity() {
        //Empty Constructor
    }


    public long getPhoneNumer() {

        return phoneNumer;
    }


    public void setPhoneNumer( long phoneNumer ) {

        this.phoneNumer = phoneNumer;
    }


    public String getAddress1() {

        return address1;
    }


    public void setAddress1( String address1 ) {

        this.address1 = address1;
    }


    public String getAddress2() {

        return address2;
    }


    public void setAddress2( String address2 ) {

        this.address2 = address2;
    }


    public String getLocation() {

        return location;
    }


    public void setLocation( String location ) {

        this.location = location;
    }


    public String getLinkedIn() {

        return linkedIn;
    }


    public void setLinkedIn( String linkedIn ) {

        this.linkedIn = linkedIn;
    }


    public String getOfficialEmail() {

        return officialEmail;
    }


    public void setOfficialEmail( String officialEmail ) {

        this.officialEmail = officialEmail;
    }


    @Override
    public boolean equals( Object o ) {

        if( this == o ) {
            return true;
        }

        if( o == null || getClass() != o.getClass() ) {
            return false;
        }

        ContactEntity contactEntity = (ContactEntity) o;

        return new EqualsBuilder().append( phoneNumer, contactEntity.phoneNumer )
                .append( address1, contactEntity.address1 ).append( address2, contactEntity.address2 )
                .append( location, contactEntity.location ).append( linkedIn, contactEntity.linkedIn )
                .append( officialEmail, contactEntity.officialEmail ).isEquals();
    }


    @Override
    public int hashCode() {

        return new HashCodeBuilder( 17, 37 ).append( phoneNumer ).append( address1 ).append( address2 )
                .append( location ).append( linkedIn ).append( officialEmail ).toHashCode();
    }


    @Override
    public String toString() {

        return "{" + "phoneNumer=" + phoneNumer + ", address1='" + address1 + '\'' + ", address2='" + address2 + '\'' +
                ", location='" + location + '\'' + ", linkedIn='" + linkedIn + '\'' + ", officialEmail='" +
                officialEmail + '\'' + '}';
    }

}
