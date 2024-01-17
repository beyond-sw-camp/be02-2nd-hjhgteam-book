package com.example.demo.emailcert.application.service;

import com.example.demo.common.UseCase;
import com.example.demo.emailcert.adapter.out.persistence.EmailCertJpaEntity;
import com.example.demo.emailcert.application.port.in.CreateEmailCertCommand;
import com.example.demo.emailcert.application.port.in.CreateEmailCertUseCase;
import com.example.demo.emailcert.application.port.out.CreateEmailCertPort;
import com.example.demo.emailcert.application.port.out.SendEmailPort;
import com.example.demo.emailcert.domain.EmailCert;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
//@Service
@RequiredArgsConstructor
public class CreateEmailCertService implements CreateEmailCertUseCase {
    private final CreateEmailCertPort createEmailCertPort;
    private final SendEmailPort sendEmailPort;

    @Override
    public void createEmailCert(CreateEmailCertCommand command) {
        String uuid = UUID.randomUUID().toString();
        EmailCert emailCert = EmailCert.builder()
                .email(command.getEmail())
                .uuid(uuid)
                .build();
        createEmailCertPort.createEmailCert(emailCert);

        sendEmailPort.sendEmail(emailCert);
    }

}
