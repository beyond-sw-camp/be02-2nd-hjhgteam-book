package com.example.demo.emailcert.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;


@Builder
@Data
public class CreateEmailCertCommand {
    @NonNull
    private final String email;

    public CreateEmailCertCommand(@NonNull String email) {
        this.email = email;
    }
}
