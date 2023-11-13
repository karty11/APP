package com.keystoneconstructs.credentia.pojo;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table( name = "certificate_entity" )
public class CertificateEntity extends AuditFields {

    @Id
    private String id;

    @Column( name = "issuing_authority" )
    private String issuingAuthority;

    @Column( name = "company_branding" )
    private String companyBranding;

    @Column( name = "certificate_name" )
    private String name;

    @Column( name = "certification_criteria" )
    private String certificationCriteria;

    @Column( name = "revoke_criteria" )
    private String revokeCriteria;

    @Column( name = "course_url" )
    private String courseUrl;

    @Column( name = "website" )
    private String website;

    @ManyToOne
    @JoinColumn( name = "organization_id", nullable = false )
    private OrganizationEntity organization;


    public String getId() {

        return id;
    }


    public void setId( String id ) {

        this.id = id;
    }


    public String getIssuingAuthority() {

        return issuingAuthority;
    }


    public void setIssuingAuthority( String issuingAuthority ) {

        this.issuingAuthority = issuingAuthority;
    }


    public String getCompanyBranding() {

        return companyBranding;
    }


    public void setCompanyBranding( String companyBranding ) {

        this.companyBranding = companyBranding;
    }


    public String getName() {

        return name;
    }


    public void setName( String name ) {

        this.name = name;
    }


    public String getCertificationCriteria() {

        return certificationCriteria;
    }


    public void setCertificationCriteria( String certificationCriteria ) {

        this.certificationCriteria = certificationCriteria;
    }


    public String getRevokeCriteria() {

        return revokeCriteria;
    }


    public void setRevokeCriteria( String revokeCriteria ) {

        this.revokeCriteria = revokeCriteria;
    }


    public String getCourseUrl() {

        return courseUrl;
    }


    public void setCourseUrl( String courseUrl ) {

        this.courseUrl = courseUrl;
    }


    public String getWebsite() {

        return website;
    }


    public void setWebsite( String website ) {

        this.website = website;
    }


    public OrganizationEntity getOrganization() {

        return organization;
    }


    public void setOrganization( OrganizationEntity organization ) {

        this.organization = organization;
    }


    @Override
    public boolean equals( Object o ) {

        if( this == o ) {
            return true;
        }

        if( o == null || getClass() != o.getClass() ) {
            return false;
        }

        CertificateEntity that = (CertificateEntity) o;

        return new EqualsBuilder().append( id, that.id ).append( issuingAuthority, that.issuingAuthority )
                .append( companyBranding, that.companyBranding ).append( name, that.name )
                .append( certificationCriteria, that.certificationCriteria )
                .append( revokeCriteria, that.revokeCriteria ).append( courseUrl, that.courseUrl )
                .append( website, that.website ).append( organization, that.organization ).isEquals();
    }


    @Override
    public int hashCode() {

        return new HashCodeBuilder( 17, 37 ).append( id ).append( issuingAuthority ).append( companyBranding )
                .append( name ).append( certificationCriteria ).append( revokeCriteria ).append( courseUrl )
                .append( website ).append( organization ).toHashCode();
    }


    @Override
    public String toString() {

        return "{" + "id='" + id + '\'' + ", issuingAuthority='" + issuingAuthority + '\'' + ", companyBranding='" +
                companyBranding + '\'' + ", name='" + name + '\'' + ", certificationCriteria='" +
                certificationCriteria + '\'' + ", revokeCriteria='" + revokeCriteria + '\'' + ", courseUrl='" +
                courseUrl + '\'' + ", website='" + website + '\'' + ", organization=" + organization + '}';
    }

}
