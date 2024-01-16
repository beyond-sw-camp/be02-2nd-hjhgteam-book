package com.example.demo.collection.repository;

import com.example.demo.collection.model.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
//    public Optional<Collection> findByCollectionTitle(String collectionTitle);

    Page<Collection> findByCollectionTitleContaining(String searchKeyword, Pageable pageable);

}
