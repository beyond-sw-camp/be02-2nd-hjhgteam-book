package com.example.demo.member.controller;

import com.example.demo.member.model.Membership;
import com.example.demo.member.model.dto.request.*;
import com.example.demo.member.model.dto.response.MemberLoginRes;
import com.example.demo.member.service.EmailAuthenticationService;
import com.example.demo.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Api(value = "회원 컨트롤러", tags = "회원 API")
public class MemberController {
    private final MemberService service;
    private final EmailAuthenticationService emailAuthenticationService;

    @ApiOperation(value = "회원가입")
    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity signup(MemberSignupReq memberSignupReq) {
        Boolean result = service.signup(memberSignupReq);
        if (result) {
            service.sendEmail(memberSignupReq);
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.ok().body("fail");
    }

    @ApiOperation(value = "이메일 인증")
    @RequestMapping(method = RequestMethod.GET, value = "/verify")
    public ResponseEntity verifyEmail(EmailAuthenticationReq emailAuthenticaitonReq) {
        if (emailAuthenticationService.verifyEmail(emailAuthenticaitonReq)) {
            service.updateStatus(emailAuthenticaitonReq.getEmail());
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.ok().body("fail");
    }
    @ApiOperation(value = "로그인")
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity login(MemberLoginReq memberLoginReq) {
        MemberLoginRes response = MemberLoginRes.builder()
                .token(service.login(memberLoginReq))
                .build();
        return ResponseEntity.ok().body(response);
    }
    @ApiOperation(value = "회원 정보 수정")
    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(MemberUpdateReq memberUpdateReq) {
        String path = null;
        if (memberUpdateReq.getImageFile() != null)
            path = service.saveFile(memberUpdateReq.getImageFile());

        service.update(memberUpdateReq, path);
        return ResponseEntity.ok().body("ok");
    }

    @ApiOperation(value = "멤버십 생성")
    @RequestMapping(method = RequestMethod.POST, value = "/createmembership")
    public ResponseEntity createMembership(MembershipReq membershipReq) {
        service.createMembership(membershipReq);
        return ResponseEntity.ok().body("ok");
    }

    @ApiOperation(value = "회원 멤버십 결제")
    @RequestMapping(method = RequestMethod.POST, value = "/paymembership")
    public ResponseEntity membership(String impUid) {
        try {
            service.membership(impUid);
        }catch (Exception e) {
        }

        return ResponseEntity.ok().body("결제완료");
    }
}
