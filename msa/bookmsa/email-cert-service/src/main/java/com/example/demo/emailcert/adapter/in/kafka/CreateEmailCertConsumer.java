package com.example.demo.emailcert.adapter.in.kafka;

import com.example.demo.common.ExternalSystemAdapter;
import com.example.demo.emailcert.application.port.in.CreateEmailCertCommand;
import com.example.demo.emailcert.application.port.in.CreateEmailCertUseCase;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class CreateEmailCertConsumer {
    private final CreateEmailCertUseCase createEmailCertUseCase;

    @KafkaListener(topics = "emailcert", groupId = "emailcert-group-00")
    void createEmailCert(ConsumerRecord<String, String> record) {
        CreateEmailCertCommand command = CreateEmailCertCommand.builder()
                .email(record.value())
                .build();
        createEmailCertUseCase.createEmailCert(command);
    }
}
