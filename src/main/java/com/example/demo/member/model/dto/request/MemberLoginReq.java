package com.example.demo.member.model.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
@ApiModel(value = "로그인 요청")
public class MemberLoginReq {
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]+$")
    @Size(min = 10, max = 40)
    @ApiParam(value = "회원의 이메일을 입력", required = true, example = "test01@test.com")
    String email;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{10,30}$")
    @ApiParam(value = "회원의 패스워드를 입력", required = true, example = "Qwer!12@34")
    String password;
}
