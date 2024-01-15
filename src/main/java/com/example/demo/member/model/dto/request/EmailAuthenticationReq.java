package com.example.demo.member.model.dto.request;

import com.example.demo.member.model.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
@ApiModel(value = "이메일 인증 요청")
public class EmailAuthenticationReq {
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]+$")
    @Size(min = 10, max = 40)
    @ApiParam(value = "회원의 이메일을 입력", required = true, example = "test01@test.com")
    private String email;
    @ApiParam(value = "회원의 UUID를 입력", required = true)
    private String uuid;
    @ApiParam(value = "회원의 JWT를 입력", required = true)
    private String jwt;
}
