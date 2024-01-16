package com.example.demo.member.repository;

import com.example.demo.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    public Optional<Member> findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT nickname FROM MEMBER WHERE id = :userId")
    public String getNickName(Long userId);
}
