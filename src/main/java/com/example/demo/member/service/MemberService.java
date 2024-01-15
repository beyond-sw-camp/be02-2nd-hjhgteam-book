package com.example.demo.member.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.demo.member.model.Member;
import com.example.demo.member.model.Membership;
import com.example.demo.member.model.dto.request.*;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.repository.MembershipRepository;
import com.example.demo.utils.JwtUtils;
import com.google.gson.Gson;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender emailSender;
    private final EmailAuthenticationService emailAuthenticationService;
    private final AmazonS3 s3;
    private final IamportClient iamportClient;
    private final MembershipRepository membershipRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private int expiredTimeMs;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public Boolean signup(MemberSignupReq memberSignupReq) {
        if (!repository.findByEmail(memberSignupReq.getEmail()).isPresent()) {
            Member member = repository.save(Member.builder()
                    .email(memberSignupReq.getEmail())
                    .password(passwordEncoder.encode(memberSignupReq.getPassword()))
                    .image(null)
                    .nickname(null)
                    .authority("ROLE_USER")
                    .status(false)
                    .build());
            return true;
        }
        return false;
    }

    public void sendEmail(MemberSignupReq memberSignupReq) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(memberSignupReq.getEmail());
        message.setSubject("[심마켓] 이메일 인증");
        String uuid = UUID.randomUUID().toString();
        String jwt = JwtUtils.generateAccessToken(memberSignupReq.getEmail(), secretKey, expiredTimeMs);
        message.setText("http://localhost:8080/member/verify?email="
                +memberSignupReq.getEmail()
                +"&uuid="+uuid
                +"&jwt="+jwt
        );
        emailSender.send(message);

        emailAuthenticationService.createEmailVerify(EmailAuthenticationReq.builder()
                .email(memberSignupReq.getEmail())
                .uuid(uuid)
                .jwt(jwt).build());
    }

    public String login(MemberLoginReq memberLoginReq) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(memberLoginReq.getEmail(), memberLoginReq.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return JwtUtils.generateAccessToken(memberLoginReq.getEmail(), secretKey, expiredTimeMs);
        }
        return null;
    }

    public void updateStatus(String email) {
        Optional<Member> result = repository.findByEmail(email);
        if (result.isPresent()) {
            Member member = result.get();
            member.setStatus(true);
            repository.save(member);
        }
    }

    public void update(MemberUpdateReq memberUpdateReq, String path) {
        Optional<Member> result = repository.findByEmail(memberUpdateReq.getEmail());
        if (result.isPresent()) {
            Member member = result.get();
            if (memberUpdateReq.getPassword() != null)
                member.setPassword(memberUpdateReq.getPassword());
            if (memberUpdateReq.getNickname() != null)
                member.setNickname(memberUpdateReq.getNickname());
            if (path != null)
                member.setImage(path);
            repository.save(member);
        }
    }

    public String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        return folderPath;
    }

    public String saveFile(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        String folderPath = makeFolder();
        String uuid = UUID.randomUUID().toString();

        String saveFileName = folderPath + File.separator + uuid + "_" + originalName;

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            s3.putObject(bucket, saveFileName.replace(File.separator, "/"), file.getInputStream(), metadata);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return s3.getUrl(bucket, saveFileName.replace(File.separator, "/")).toString();
    }

    public Boolean membership(String impUid) throws IamportResponseException, IOException {
        IamportResponse<Payment> response = getPaymentInfo(impUid);
        Integer amount = response.getResponse().getAmount().intValue();

        String customDataString = response.getResponse().getCustomData();
        Gson gson = new Gson();
        MembershipPayment membershipPayment = gson.fromJson(customDataString, MembershipPayment.class);

        Integer membershipPrice = getMembershipPrice(membershipPayment);

        if (amount.equals(membershipPrice)) {
            repository.save(Member.builder()
                    .authority(membershipPayment.getAuthority())
                    .build());
            return true;
        }

        return false;
    }

    public Integer getMembershipPrice(MembershipPayment membershipPayment) {
        Optional<Membership> membership = membershipRepository.findByAuthority(membershipPayment.getAuthority());
        return membership.get().getMembershipPrice();
    }

    public IamportResponse getPaymentInfo(String impUid) throws IamportResponseException, IOException {
        IamportResponse<Payment> response = iamportClient.paymentByImpUid(impUid);

        return response;
    }

    public Member getMemberByEmail(String email) {
        Optional<Member> result = repository.findByEmail(email);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }
}
