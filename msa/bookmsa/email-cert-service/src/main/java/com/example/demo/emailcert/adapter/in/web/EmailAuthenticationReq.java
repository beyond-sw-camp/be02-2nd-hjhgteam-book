package com.example.demo.emailcert.adapter.in.web;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailAuthenticationReq {

    private String email;

    private String uuid;

}
