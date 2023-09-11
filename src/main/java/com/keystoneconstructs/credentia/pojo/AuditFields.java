package com.keystoneconstructs.credentia.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@MappedSuperclass
public class AuditFields {

//    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

//    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

}
