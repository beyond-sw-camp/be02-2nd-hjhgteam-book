package com.example.demo.member.service;

import com.example.demo.member.model.EmailAuthentication;
import com.example.demo.member.model.dto.request.EmailAuthenticationReq;
import com.example.demo.member.repository.EmailAuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailAuthenticationService {
    private final EmailAuthenticationRepository repository;
    public void createEmailVerify(EmailAuthenticationReq emailAuthenticationReq) {
        repository.save(EmailAuthentication.builder()
                .uuid(emailAuthenticationReq.getUuid())
                .jwt(emailAuthenticationReq.getJwt())
                .email(emailAuthenticationReq.getEmail())
                .build());
    }

    public Boolean verifyEmail(EmailAuthenticationReq emailAuthenticationReq) {
        Optional<EmailAuthentication> result = repository.findByEmail(emailAuthenticationReq.getEmail());
        if (result.isPresent()) {
            EmailAuthentication emailAuthentication = result.get();
            if (emailAuthentication.getUuid().equals(emailAuthenticationReq.getUuid()))
                if (emailAuthentication.getJwt().equals(emailAuthenticationReq.getJwt()))
                    return true;
        }
        return false;
    }
}
