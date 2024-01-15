package com.example.demo.member.repository;

import com.example.demo.member.model.EmailAuthentication;
import com.example.demo.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthenticationRepository extends JpaRepository<EmailAuthentication, Long> {
    public Optional<EmailAuthentication> findByEmail(String email);
}
