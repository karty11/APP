package com.keystoneconstructs.credentia.repository;

import com.keystoneconstructs.credentia.pojo.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends JpaRepository<CertificateEntity, String> {


    Optional<CertificateEntity> findByNameIgnoreCase(String name);


    List<CertificateEntity> findAllByOrganization_id(String orgId);
}
