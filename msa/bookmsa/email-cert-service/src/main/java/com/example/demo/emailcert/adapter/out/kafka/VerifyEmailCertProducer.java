package com.example.demo.emailcert.adapter.out.kafka;

import com.example.demo.common.ExternalSystemAdapter;
import com.example.demo.emailcert.application.port.out.VerifyCertEventPort;
import com.example.demo.emailcert.domain.EmailCert;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class VerifyEmailCertProducer implements VerifyCertEventPort {
    private final KafkaTemplate kafkaTemplate;

    @Override
    public EmailCert verifyEmailCertEvent(EmailCert emailCert) {
//        VerifiedEmail verifiedEmail = VerifiedEmail.builder()
//                .email(emailCert.getEmail())
//                .status(true)
//                .build();
        ProducerRecord<String,String> record =
                new ProducerRecord<>("emailverified","cert", emailCert.getEmail());

        kafkaTemplate.send(record);
        return emailCert;
    }
}
