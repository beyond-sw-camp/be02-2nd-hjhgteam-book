package com.example.demo.emailcert.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;


@Builder
@Data
public class VerifyEmailCertCommand {
    @NonNull
    private final String email;
    private final String uuid;

    public VerifyEmailCertCommand(@NonNull String email, String uuid) {
        this.email = email;
        this.uuid = uuid;
    }
}
