package com.keystoneconstructs.credentia.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public class AuditFields {

    @Column( name = "created_by" )
    private String createdBy;

    @CreationTimestamp
    @Column( name = "created_on" )
    private LocalDateTime createdOn;

    @Column( name = "updated_by" )
    private String updatedBy;

    @UpdateTimestamp
    @Column( name = "updated_on" )
    private LocalDateTime updatedOn;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy( String createdBy ) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn( LocalDateTime createdOn ) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy( String updatedBy ) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn( LocalDateTime updatedOn ) {
        this.updatedOn = updatedOn;
    }

}
