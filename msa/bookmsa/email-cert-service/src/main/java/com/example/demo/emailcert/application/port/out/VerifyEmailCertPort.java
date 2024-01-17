package com.example.demo.emailcert.application.port.out;

import com.example.demo.emailcert.adapter.out.persistence.EmailCertJpaEntity;
import com.example.demo.emailcert.domain.EmailCert;

public interface VerifyEmailCertPort {
    Boolean verifyEmailCert(EmailCert emailCert);
}
