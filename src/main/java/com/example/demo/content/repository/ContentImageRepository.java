package com.example.demo.content.repository;

import com.example.demo.content.model.ContentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ContentImageRepository extends JpaRepository<ContentImage, Long> {

    Optional<ContentImage> findByContent_Id(Long id);

    @Modifying
    @Transactional
    @Override
    void deleteById(Long id);
}
