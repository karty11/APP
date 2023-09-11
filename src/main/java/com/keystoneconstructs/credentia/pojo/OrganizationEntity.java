package com.keystoneconstructs.credentia.pojo;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

@Entity
@Table( name = "organization_entity" )
public class OrganizationEntity extends AuditFields {

    @Id
    private String id;

    @Column( name = "name" )
    private String name;

    @Column( name = "org_code" )
    private String orgCode;

    @Column( name = "industry" )
    private String industry;

    @Type( JsonType.class )
    @Column( name = "contact", columnDefinition = "json" )
    private ContactEntity contactEntity;

    public OrganizationEntity() {
        //Empty Constructor
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode( String orgCode ) {
        this.orgCode = orgCode;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry( String industry ) {
        this.industry = industry;
    }

    public ContactEntity getContact() {
        return contactEntity;
    }

    public void setContact( ContactEntity contactEntity ) {
        this.contactEntity = contactEntity;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;

        if ( o == null || getClass() != o.getClass() ) return false;

        OrganizationEntity that = ( OrganizationEntity ) o;

        return new EqualsBuilder().append( id, that.id ).append( name, that.name ).append( orgCode, that.orgCode )
                .append( industry, that.industry ).append( contactEntity, that.contactEntity ).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder( 17, 37 ).append( id ).append( name ).append( orgCode ).append( industry )
                .append( contactEntity ).toHashCode();
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", industry='" + industry + '\'' +
                ", contact=" + contactEntity +
                '}';
    }

}
