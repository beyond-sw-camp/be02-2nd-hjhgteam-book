package com.example.demo.emailcert.adapter.in.web;

import com.example.demo.common.ExternalSystemAdapter;
import com.example.demo.common.WebAdapter;
import com.example.demo.emailcert.application.port.in.VerifyEmailCertCommand;
import com.example.demo.emailcert.application.port.in.VerifyEmailCertUseCase;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class VerifyEmailCertConsumer {
    private final VerifyEmailCertUseCase verifyEmailCertUseCase;
    @RequestMapping(method = RequestMethod.GET, value = "/email/verify")
    Boolean verifyEmail(EmailAuthenticationReq emailAuthenticationReq) {

        VerifyEmailCertCommand command = VerifyEmailCertCommand.builder()
                .email(emailAuthenticationReq.getEmail())
                .uuid(emailAuthenticationReq.getUuid())
                .build();

        return verifyEmailCertUseCase.verifyEmailCert(command);
    }
}
