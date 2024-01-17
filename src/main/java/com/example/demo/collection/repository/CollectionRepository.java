package com.example.demo.collection.repository;

import com.example.demo.collection.model.Collection;
import com.example.demo.collection.repository.querydsl.CollectionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Long>, CollectionRepositoryCustom {
//    public Optional<Collection> findByCollectionTitle(String collectionTitle);


    // todo 검색
    List<Collection> findByCollectionTitleContaining(String keyword);



    public List<Collection> findAllByCollectionTitle(String collectionTitle);


    @Modifying
    @Transactional
    @Override
    void deleteById(Long idx);




}
