package com.example.demo.category.repository.querydsl;

import com.example.demo.category.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryRepositoryCustom {
    public Page<Category> findList(Pageable pageable);
}
