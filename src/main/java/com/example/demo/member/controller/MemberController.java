package com.example.demo.member.controller;

import com.example.demo.member.model.Membership;
import com.example.demo.member.model.dto.request.*;
import com.example.demo.member.model.dto.response.MemberLoginRes;
import com.example.demo.member.service.EmailAuthenticationService;
import com.example.demo.member.service.MemberService;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Api(value = "회원 컨트롤러", tags = "회원 API")
@CrossOrigin("*")
public class MemberController {
    private final MemberService service;
    private final EmailAuthenticationService emailAuthenticationService;

    @Value("${imp.imp_key}")
    private String apiKey;
    @Value("${imp.imp_secret}")
    private String apiSecret;

    @ApiOperation(value = "회원가입")
    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity signup(@RequestBody MemberSignupReq memberSignupReq) {
        Boolean result = service.signup(memberSignupReq);
        if (result) {
            service.createEmailCert(memberSignupReq);
            service.sendEmail(memberSignupReq);
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.ok().body("fail");
    }

//    @KafkaListener(topics = "emailverified", groupId = "emailverified-group-00")
//    void createEmailCert(ConsumerRecord<String, String> record) {
//        service.updateStatus(record.value());
//    }

    @ApiOperation(value = "이메일 인증")
    @RequestMapping(method = RequestMethod.GET, value = "/verify")
    public ResponseEntity verifyEmail(EmailAuthenticationReq emailAuthenticaitonReq) {
        if (service.emailCert(emailAuthenticaitonReq)) {
            service.updateStatus(emailAuthenticaitonReq.getEmail());
        }
        return ResponseEntity.ok().body(service.emailCert(emailAuthenticaitonReq));
    }

    @ApiOperation(value = "로그인")
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity login(@RequestBody MemberLoginReq memberLoginReq) {
        MemberLoginRes response = MemberLoginRes.builder()
                .token(service.login(memberLoginReq))
                .build();
        if (response.getToken() != null) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.ok().body("로그인 실패");
        }
    }
    @ApiOperation(value = "회원 정보 수정")
    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(
            MemberUpdateReq memberUpdateReq,
            @RequestPart MultipartFile imageFile) {
        String path = null;
        if (imageFile != null)
            path = service.saveFile(imageFile);

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
    @RequestMapping(method = RequestMethod.GET, value = "/paymembership")
    public ResponseEntity membership(String impUid) {
        try {
            if (service.membership(impUid)) {
                return ResponseEntity.ok().body("결제완료");
            } else {
                String reason = "결제 실패";
                IamportResponse<Payment> response = service.getPaymentInfo(impUid);
                service.refundRequest(service.getToken(apiKey, apiSecret), response.getResponse().getMerchantUid(), reason);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body("결제실패");
    }

}
