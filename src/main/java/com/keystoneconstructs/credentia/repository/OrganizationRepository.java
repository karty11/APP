package com.keystoneconstructs.credentia.repository;

import com.keystoneconstructs.credentia.pojo.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, String> {

    Optional<OrganizationEntity> findByNameIgnoreCase( String name );

}
