package com.keystoneconstructs.credentia.pojo;

import com.google.gson.annotations.Expose;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table( name = "user_entity" )
public class UserEntity extends AuditFields implements UserDetails {

    @Id
    @Expose
    private String id;

    @Expose
    @Column( name = "first_name" )
    private String firstName;

    @Expose
    @Column( name = "last_name" )
    private String lastName;

    @Expose
    @Column( name = "initials" )
    private String initials;

    @Expose
    @Column( name = "role" )
    private String role;

    @Expose
    @Type( JsonType.class )
    @Column( name = "contact", columnDefinition = "json" )
    private ContactEntity contactEntity;

    @Expose
    @Column( name = "email" )
    private String email;

    @Expose
    @Column( name = "user_group_id" )
    private String userGroupId;

    @Expose
    @Column( name = "is_deleted" )
    private boolean deleted;

    @Expose
    @Column( name = "encryption_key" )
    private String salt;

    @Expose
    @Column( name = "encrypted_key" )
    private String encryptedPassword;

    @Expose
    @ManyToOne
    @JoinColumn( name = "organization_id", nullable = false )
    private OrganizationEntity organization;


    public UserEntity() {
        //Empty Constructor
    }


    public String getId() {

        return id;
    }


    public void setId( String id ) {

        this.id = id;
    }


    public String getFirstName() {

        return firstName;
    }


    public void setFirstName( String firstName ) {

        this.firstName = firstName;
    }


    public String getLastName() {

        return lastName;
    }


    public void setLastName( String lastName ) {

        this.lastName = lastName;
    }


    public String getInitials() {

        return initials;
    }


    public void setInitials( String initials ) {

        this.initials = initials;
    }


    public String getRole() {

        return role;
    }


    public void setRole( String role ) {

        this.role = role;
    }


    public String getEmail() {

        return email;
    }


    public void setEmail( String email ) {

        this.email = email;
    }


    public String getUserGroupId() {

        return userGroupId;
    }


    public void setUserGroupId( String userGroupId ) {

        this.userGroupId = userGroupId;
    }


    public ContactEntity getContactEntity() {

        return contactEntity;
    }


    public void setContactEntity( ContactEntity contactEntity ) {

        this.contactEntity = contactEntity;
    }


    public boolean isDeleted() {

        return deleted;
    }


    public void setDeleted( boolean deleted ) {

        this.deleted = deleted;
    }


    public OrganizationEntity getOrganization() {

        return organization;
    }


    public void setOrganization( OrganizationEntity organization ) {

        this.organization = organization;
    }


    public String getSalt() {

        return salt;
    }


    public void setSalt( String salt ) {

        this.salt = salt;
    }


    public String getEncryptedPassword() {

        return encryptedPassword;
    }


    public void setEncryptedPassword( String encryptedPassword ) {

        this.encryptedPassword = encryptedPassword;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singleton( new SimpleGrantedAuthority( role ) );
    }


    @Override
    public String getPassword() {

        return encryptedPassword;
    }


    @Override
    public String getUsername() {

        return email;
    }


    @Override
    public boolean isAccountNonExpired() {

        return isDeleted();
    }


    @Override
    public boolean isAccountNonLocked() {

        return isDeleted();
    }


    @Override
    public boolean isCredentialsNonExpired() {

        return isDeleted();
    }


    @Override
    public boolean isEnabled() {

        return isDeleted();
    }


    @Override
    public int hashCode() {

        return new HashCodeBuilder( 17, 37 ).append( id ).append( firstName ).append( lastName ).append( initials )
                .append( role ).append( contactEntity ).append( email ).append( userGroupId ).append( deleted )
                .append( salt ).append( encryptedPassword ).append( organization ).toHashCode();
    }


    @Override
    public String toString() {

        return "{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' +
                ", initials='" + initials + '\'' + ", role='" + role + '\'' + ", contactEntity=" + contactEntity +
                ", email='" + email + '\'' + ", userGroupId='" + userGroupId + '\'' + ", deleted=" + deleted +
                ", salt='" + salt + '\'' + ", encryptedPassword='" + encryptedPassword + '\'' + ", organization=" +
                organization + '}';
    }


    @Override
    public boolean equals( Object o ) {

        if( this == o ) {
            return true;
        }

        if( o == null || getClass() != o.getClass() ) {
            return false;
        }

        UserEntity that = (UserEntity) o;

        return new EqualsBuilder().append( deleted, that.deleted ).append( id, that.id )
                .append( firstName, that.firstName ).append( lastName, that.lastName ).append( initials, that.initials )
                .append( role, that.role ).append( contactEntity, that.contactEntity ).append( email, that.email )
                .append( userGroupId, that.userGroupId ).append( salt, that.salt )
                .append( encryptedPassword, that.encryptedPassword ).append( organization, that.organization )
                .isEquals();

    }

}
