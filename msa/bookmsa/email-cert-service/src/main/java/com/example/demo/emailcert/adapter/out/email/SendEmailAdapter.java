package com.example.demo.emailcert.adapter.out.email;

import com.example.demo.common.ExternalSystemAdapter;
import com.example.demo.emailcert.application.port.out.SendEmailPort;
import com.example.demo.emailcert.domain.EmailCert;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class SendEmailAdapter implements SendEmailPort {
    private final JavaMailSender emailSender;

    @Override
    public void sendEmail(EmailCert emailCert) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailCert.getEmail());
        message.setSubject("[BOOKSPEDIA] 이메일 인증");
        message.setText("http://localhost:8082/email/verify"
                +"?email="+emailCert.getEmail()
                +"&uuid="+emailCert.getUuid()
        );
        emailSender.send(message);
    }
}
