package com.example.demo.collection.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.demo.collection.model.Collection;

import com.example.demo.collection.model.dto.*;
import com.example.demo.collection.repository.CollectionRepository;
import com.example.demo.member.model.Member;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository collectionRepository;




    public Collection create(Member member, CollectionCreateReq collectionCreateReq) {

        return collectionRepository.save(Collection.builder()
                .collectionTitle(collectionCreateReq.getCollectionTitle())
                .member(member)
                .build());
    }


    public CollectionListRes list() {
        List<Collection> result = collectionRepository.findAll();

        List<CollectionRes> collectionList = new ArrayList<>();
        for (Collection collection : result) {

            CollectionRes collectionListRes = CollectionRes.builder()
                    .id(collection.getId())
                    .collectionTitle(collection.getCollectionTitle())
                    .build();
            collectionList.add(collectionListRes);
        }


        return CollectionListRes.builder()
                .resultList(collectionList)
                .build();
    }

    // 검색
    public Page<Collection> collectionList(Pageable pageable){
        return collectionRepository.findAll(pageable);
    }
    public Page<Collection> collectionSearchList(String searchKeyword, Pageable pageable){
        return collectionRepository.findByCollectionTitleContaining(searchKeyword, pageable);
    }

    public CollectionRes read(Long id) {
        Optional<Collection> result = collectionRepository.findById(id);

        if (result.isPresent()) {
            Collection collection = result.get();

            CollectionRes collectionRes = CollectionRes.builder()
                    .id(collection.getId())
                    .collectionTitle(collection.getCollectionTitle())
                    .build();
            return collectionRes;
        }

        return null;
    }

    public void update(CollectionUpdateReq collectionUpdateReq) {
        Optional<Collection> result = collectionRepository.findById(collectionUpdateReq.getId());
        if (result.isPresent()) {
            Collection collection = result.get();
            collection.setCollectionTitle(collectionUpdateReq.getCollectionTitle());

            collectionRepository.save(collection);
        }
    }

    public void delete(Long id) {
        collectionRepository.delete(Collection.builder().id(id).build());
    }

}
