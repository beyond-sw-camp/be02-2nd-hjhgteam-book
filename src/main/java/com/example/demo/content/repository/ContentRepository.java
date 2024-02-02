package com.example.demo.content.repository;

import com.example.demo.content.model.Content;
import com.example.demo.content.model.ContentImage;
import com.example.demo.content.repository.querydsl.ContentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Long>, ContentRepositoryCustom {

    Optional<Content> findByName(String name);
}
