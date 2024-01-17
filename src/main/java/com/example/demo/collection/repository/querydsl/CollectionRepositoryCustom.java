package com.example.demo.collection.repository.querydsl;

import com.example.demo.collection.model.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollectionRepositoryCustom {
    public Page<Collection> findList(Pageable pageable);
}
