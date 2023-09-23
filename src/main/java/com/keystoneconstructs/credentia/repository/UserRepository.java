package com.keystoneconstructs.credentia.repository;

import com.keystoneconstructs.credentia.pojo.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmailIgnoreCase( String email );

    List<UserEntity> findAllByUserGroupId( String userGroupId );

    List<UserEntity> findAllByOrganization_id( String organizationId );

}
