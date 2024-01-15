package com.example.demo.member.model.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MembershipPayment {
    private String email;
    private Integer membershipPrice;
    private String authority;
}
