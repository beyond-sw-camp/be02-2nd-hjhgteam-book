package com.example.demo.content.repository.querydsl;

import com.example.demo.content.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentRepositoryCustom {
    public Page<Content> findList(Pageable pageable);
}
