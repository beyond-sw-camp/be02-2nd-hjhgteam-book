package com.example.demo.emailcert.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EmailCert {
    private final Long id;
    private final String email;
    private final String uuid;
}
