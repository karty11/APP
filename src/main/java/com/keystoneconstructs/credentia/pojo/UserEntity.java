package com.keystoneconstructs.credentia.pojo;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "user_entity")
public class UserEntity extends AuditFields {

    @Id
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "initials")
    private String initials;

    @Column(name = "role")
    private String role;

    @Type(JsonType.class)
    @Column(name = "contact", columnDefinition = "json")
    private Contact contact;

    @Column(name = "email")
    private String email;

    @Column(name = "user_group_id")
    private String userGroupId;

}
