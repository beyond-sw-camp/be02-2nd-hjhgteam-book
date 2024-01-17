package com.example.demo.emailcert.adapter.out.persistence;

import com.example.demo.common.PersistenceAdapter;
import com.example.demo.emailcert.application.port.out.CreateEmailCertPort;
import com.example.demo.emailcert.application.port.out.VerifyEmailCertPort;
import com.example.demo.emailcert.domain.EmailCert;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class EmailCertPersistenceAdapter implements CreateEmailCertPort, VerifyEmailCertPort {
    private final SpringDataEmailCertRepository emailCertRepository;

    @Override
    public EmailCertJpaEntity createEmailCert(EmailCert emailCert) {
        return emailCertRepository.save(
                EmailCertJpaEntity.builder()
                        .email(emailCert.getEmail())
                        .uuid(emailCert.getUuid())
                        .build()
        );
    }

    @Override
    public Boolean verifyEmailCert(EmailCert emailCert) {
        Optional<EmailCertJpaEntity> result = emailCertRepository.findByEmail(emailCert.getEmail());
        if (result.isPresent()) {
            EmailCertJpaEntity emailCertJpaEntity = result.get();

            if (emailCertJpaEntity.getUuid().equals(emailCert.getUuid()))
                    return true;
        }
        return false;
    }
}
