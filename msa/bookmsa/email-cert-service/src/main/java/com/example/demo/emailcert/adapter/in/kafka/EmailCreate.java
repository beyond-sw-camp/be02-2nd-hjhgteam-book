package com.example.demo.emailcert.adapter.in.kafka;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailCreate {
    String email;
    String jwt;
}
