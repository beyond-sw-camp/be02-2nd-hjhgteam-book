package com.example.demo.emailcert.application.port.out;

import com.example.demo.emailcert.adapter.out.persistence.EmailCertJpaEntity;
import com.example.demo.emailcert.domain.EmailCert;

public interface CreateEmailCertPort {
    EmailCertJpaEntity createEmailCert(EmailCert emailCert);
}
