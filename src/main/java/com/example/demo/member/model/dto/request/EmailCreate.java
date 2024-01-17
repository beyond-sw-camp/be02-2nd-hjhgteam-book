package com.example.demo.member.model.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailCreate {
    private String email;
    private String jwt;
}
