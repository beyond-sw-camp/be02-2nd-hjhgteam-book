package com.example.demo.category.repository;

import com.example.demo.category.model.Category;
import com.example.demo.category.repository.querydsl.CategoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> , CategoryRepositoryCustom {


    @Modifying
    @Transactional
    @Override
    void deleteById(Long id);
}
