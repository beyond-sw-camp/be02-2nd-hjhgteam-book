package com.example.demo.member.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.demo.member.model.Member;
import com.example.demo.member.model.Membership;
import com.example.demo.member.model.dto.request.*;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.repository.MembershipRepository;
import com.example.demo.common.utils.JwtUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
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
    private final AmazonS3Client amazonS3Client;
    private final IamportClient iamportClient;
    private final MembershipRepository membershipRepository;
//    private final KafkaTemplate kafkaTemplate;


    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private int expiredTimeMs;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void createEmailCert(MemberSignupReq memberSignupReq) {
        String jwt = JwtUtils.generateSignUpAccessToken(memberSignupReq.getEmail(), secretKey, expiredTimeMs);
        EmailCreate emailCreate = EmailCreate.builder()
                .email(memberSignupReq.getEmail())
                .jwt(jwt).build();
//        ProducerRecord<String, String> record =
//                new ProducerRecord<>("emailcert","email", emailCreate.getEmail());
//        kafkaTemplate.send(record);
    }

//    public void emailCert(EmailAuthenticationReq emailAuthenticationReq) {
//        ProducerRecord<String,String> record =
//                new ProducerRecord<>("emailverify","authentication",
//                        emailAuthenticationReq.toString());
//
//        kafkaTemplate.send(record);
//    }

    public Boolean emailCert(EmailAuthenticationReq emailAuthenticationReq) {
        return emailAuthenticationService.verifyEmail(emailAuthenticationReq);
    }

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
        message.setSubject("[BOOKSPEDIA] 이메일 인증");
        String uuid = UUID.randomUUID().toString();
        String jwt = JwtUtils.generateSignUpAccessToken(memberSignupReq.getEmail(), secretKey, expiredTimeMs);
        message.setText("http://www.bookspedia.kro.kr/api/member/verify" +
                "?email="+memberSignupReq.getEmail()
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
        Optional<Member> result = repository.findByEmail(memberLoginReq.getEmail());

        if (result.isPresent()) {
            Member member = result.get();

            Boolean passwordCheck = passwordEncoder.matches(memberLoginReq.getPassword(), member.getPassword());
            if (passwordCheck) {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(memberLoginReq.getEmail(), memberLoginReq.getPassword())
                );

                if (authentication.isAuthenticated()) {
                    return JwtUtils.generateLoginAccessToken(member, secretKey, expiredTimeMs);
                }
            }
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
                member.setPassword(passwordEncoder.encode(memberUpdateReq.getPassword()));
            if (memberUpdateReq.getNickname() != null)
                member.setNickname(memberUpdateReq.getNickname());
            if (path != null)
                member.setImage(path);
            repository.save(member);
        }
    }

    public void withdraw(String email) {
        Optional<Member> result = repository.findByEmail(email);
        if (result.isPresent()) {
            Member member = result.get();
            member.setAuthority("ROLE_WITHDRAW");
            member.setStatus(false);
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

            amazonS3Client.putObject(bucket, saveFileName.replace(File.separator, "/"), file.getInputStream(), metadata);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return amazonS3Client.getUrl(bucket, saveFileName.replace(File.separator, "/")).toString();
    }

    public void createMembership(MembershipReq membershipReq) {
        membershipRepository.save(Membership.builder()
                .membershipPrice(membershipReq.getMembershipPrice())
                .authority(membershipReq.getAuthority())
                .build());
    }

    public Boolean membership(String impUid) throws IamportResponseException, IOException {
        IamportResponse<Payment> response = getPaymentInfo(impUid);
        Integer amount = response.getResponse().getAmount().intValue();

        String customDataString = response.getResponse().getCustomData();
        System.out.println("customDataString = " + customDataString);
        Gson gson = new Gson();
        MembershipPayment membershipPayment = gson.fromJson(customDataString, MembershipPayment.class);
        String authority = membershipPayment.getAuthority();
        Integer membershipPrice = getMembershipPrice(authority);

        String user = membershipPayment.getEmail();
        System.out.println("user = " + user);
        Optional<Member> result = repository.findByEmail(user);
        Member member = result.get();

        if (amount.equals(membershipPrice)) {
            member.setAuthority(authority);
            repository.save(member);
            return true;
        }
        return false;
    }

    public Integer getMembershipPrice(String authority) {
        Optional<Membership> membership = membershipRepository.findByAuthority(authority);
        if (membership.isPresent())
            return membership.get().getMembershipPrice();
        return null;
    }

    public IamportResponse getPaymentInfo(String impUid) throws IamportResponseException, IOException {
        IamportResponse<Payment> response = iamportClient.paymentByImpUid(impUid);

        return response;
    }

    public void refundRequest(String access_token, String merchant_uid, String reason) throws IOException {
        URL url = new URL("https://api.iamport.kr/payments/cancel");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        // 요청 방식을 POST로 설정
        conn.setRequestMethod("POST");

        // 요청의 Content-Type, Accept, Authorization 헤더 설정
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", access_token);

        // 해당 연결을 출력 스트림(요청)으로 사용
        conn.setDoOutput(true);

        // JSON 객체에 해당 API가 필요로하는 데이터 추가.
        JsonObject json = new JsonObject();
        json.addProperty("merchant_uid", merchant_uid);
        json.addProperty("reason", reason);

        // 출력 스트림으로 해당 conn에 요청
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString());
        bw.flush();
        bw.close();

        // 입력 스트림으로 conn 요청에 대한 응답 반환
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        br.close();
        conn.disconnect();

//        log.info("결제 취소 완료 : 주문 번호 {}", merchant_uid);
    }

    public String getToken(String apiKey, String secretKey) throws IOException {
        URL url = new URL("https://api.iamport.kr/users/getToken");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        // 요청 방식을 POST로 설정
        conn.setRequestMethod("POST");

        // 요청의 Content-Type과 Accept 헤더 설정
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        // 해당 연결을 출력 스트림(요청)으로 사용
        conn.setDoOutput(true);

        // JSON 객체에 해당 API가 필요로하는 데이터 추가.
        JsonObject json = new JsonObject();
        json.addProperty("imp_key", apiKey);
        json.addProperty("imp_secret", secretKey);

        // 출력 스트림으로 해당 conn에 요청
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString()); // json 객체를 문자열 형태로 HTTP 요청 본문에 추가
        bw.flush(); // BufferedWriter 비우기
        bw.close(); // BufferedWriter 종료

        // 입력 스트림으로 conn 요청에 대한 응답 반환
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        Gson gson = new Gson(); // 응답 데이터를 자바 객체로 변환
        String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();
        String accessToken = gson.fromJson(response, Map.class).get("access_token").toString();
        br.close(); // BufferedReader 종료

        conn.disconnect(); // 연결 종료

//        log.info("Iamport 엑세스 토큰 발급 성공 : {}", accessToken);
        return accessToken;
    }

    public Member getMemberByEmail(String email) {
        Optional<Member> result = repository.findByEmail(email);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }
}
