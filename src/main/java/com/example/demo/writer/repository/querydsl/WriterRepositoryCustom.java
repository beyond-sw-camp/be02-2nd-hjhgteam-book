package com.example.demo.writer.repository.querydsl;


import com.example.demo.writer.model.Writer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WriterRepositoryCustom {
    public Page<Writer> findList(Pageable pageable);
}
