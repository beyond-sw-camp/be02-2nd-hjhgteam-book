package com.example.demo.emailcert.application.service;

import com.example.demo.common.UseCase;
import com.example.demo.emailcert.application.port.in.CreateEmailCertCommand;
import com.example.demo.emailcert.application.port.in.CreateEmailCertUseCase;
import com.example.demo.emailcert.application.port.in.VerifyEmailCertCommand;
import com.example.demo.emailcert.application.port.in.VerifyEmailCertUseCase;
import com.example.demo.emailcert.application.port.out.CreateEmailCertPort;
import com.example.demo.emailcert.application.port.out.SendEmailPort;
import com.example.demo.emailcert.application.port.out.VerifyCertEventPort;
import com.example.demo.emailcert.application.port.out.VerifyEmailCertPort;
import com.example.demo.emailcert.domain.EmailCert;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
//@Service
@RequiredArgsConstructor
public class VerifyEmailCertService implements VerifyEmailCertUseCase {
    private final VerifyEmailCertPort verifyEmailCertPort;
    private final VerifyCertEventPort verifyCertEventPort;

    @Override
    public Boolean verifyEmailCert(VerifyEmailCertCommand command) {
        EmailCert emailCert = EmailCert.builder()
                .email(command.getEmail())
                .uuid(command.getUuid())
                .build();
        if (verifyEmailCertPort.verifyEmailCert(emailCert))
            verifyCertEventPort.verifyEmailCertEvent(emailCert);

        return verifyEmailCertPort.verifyEmailCert(emailCert);
    }


}
