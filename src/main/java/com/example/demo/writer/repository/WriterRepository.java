package com.example.demo.writer.repository;

import com.example.demo.writer.model.Writer;
import com.example.demo.writer.repository.querydsl.WriterRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface WriterRepository extends JpaRepository<Writer, Long>, WriterRepositoryCustom {

    @Modifying
    @Transactional
    @Override
    void deleteById(Long id);

}
