package com.example.demo.emailcert.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataEmailCertRepository extends JpaRepository<EmailCertJpaEntity, Long> {
    public Optional<EmailCertJpaEntity> findByEmail(String email);
}
