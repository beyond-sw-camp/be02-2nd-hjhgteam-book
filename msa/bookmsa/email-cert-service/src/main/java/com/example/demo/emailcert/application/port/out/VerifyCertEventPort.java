package com.example.demo.emailcert.application.port.out;


import com.example.demo.emailcert.domain.EmailCert;

public interface VerifyCertEventPort {
    EmailCert verifyEmailCertEvent(EmailCert emailCert);
}
