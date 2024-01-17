package com.example.demo.content.repository;

import com.example.demo.content.model.Content;
import com.example.demo.content.repository.querydsl.ContentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long>, ContentRepositoryCustom {
}
