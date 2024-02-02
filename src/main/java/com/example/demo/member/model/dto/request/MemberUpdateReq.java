package com.example.demo.member.model.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
@ApiModel(value = "회원 정보 수정 요청")
public class MemberUpdateReq {
//    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]+$")
//    @Size(min = 10, max = 40)
    @ApiParam(value = "회원의 이메일을 입력", example = "test01@test.com")
    private String email;
//    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{10,30}$")
    @ApiParam(value = "회원의 패스워드를 입력", example = "Qwer!12@34")
    private String password;
//    @Pattern(regexp = "^[a-zA-Zㄱ-ㅎ가-힣0-9]*$")
//    @Size(min = 10, max = 40)
    @ApiParam(value = "회원의 닉네임을 입력", example = "Qwer!12@34")
    private String nickname;
//    @ApiParam(value = "이미지 파일 업로드")
//    private MultipartFile imageFile;
}
