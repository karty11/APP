package com.keystoneconstructs.credentia.repository;

import com.keystoneconstructs.credentia.pojo.CertifierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertifierRepository extends JpaRepository<CertifierEntity, String> {

    List<CertifierEntity> findAllByRecipientOrganizationIgnoreCase( String recipientOrganization );

    List<CertifierEntity> findAllByCertificate_id( String certificateId );

    List<CertifierEntity> findByRecipientEmail( String recipientEmail );

}
