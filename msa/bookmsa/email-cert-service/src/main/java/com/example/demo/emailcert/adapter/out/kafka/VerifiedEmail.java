package com.example.demo.emailcert.adapter.out.kafka;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerifiedEmail {
    String email;
    Boolean status;
}
