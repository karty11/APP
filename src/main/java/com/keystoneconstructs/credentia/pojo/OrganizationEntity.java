package com.keystoneconstructs.credentia.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "organization_entity")
public class OrganizationEntity extends AuditFields{

    @Id
    private String id;

    @Column(name = "name")
    private String name;



}
