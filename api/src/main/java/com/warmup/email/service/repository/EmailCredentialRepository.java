package com.warmup.email.service.repository;

import com.warmup.email.service.EmailCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailCredentialRepository extends JpaRepository<EmailCredential, String> {
    Optional<EmailCredential> findByTenantIdAndUserId(String tenantId, String userId);
}
