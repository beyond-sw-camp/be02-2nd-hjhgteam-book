package com.example.demo.collection.service;

import com.example.demo.category.model.Category;
import com.example.demo.category.repository.CategoryRepository;
import com.example.demo.collection.model.Collection;
import com.example.demo.collection.model.dto.CollectionCreateReq;
import com.example.demo.collection.model.dto.CollectionListRes;
import com.example.demo.collection.model.dto.CollectionReadRes;
import com.example.demo.collection.model.dto.CollectionUpdateReq;
import com.example.demo.collection.repository.CollectionRepository;
import com.example.demo.content.model.Content;
import com.example.demo.content.repository.ContentRepository;
import com.example.demo.member.model.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.writer.model.Writer;
import com.example.demo.writer.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository collectionRepository;

    private final MemberRepository memberRepository;

    //todo 컬렉션에 있는 컨텍트면 추가 X
    public void create(Member member, CollectionCreateReq collectionCreateReq) {

        Optional<Member> result = memberRepository.findByEmail(member.getEmail());

        collectionRepository.save(Collection.builder()
                .collectionTitle(collectionCreateReq.getCollectionTitle())
                .member(result.get())
                .contentInCollect(Content.builder().id(collectionCreateReq.getContentIdx()).build())
                .build());
    }

    public List<CollectionReadRes> list(){
        List<CollectionReadRes> collectionReadResList = new ArrayList<>();
        List<Collection> result = collectionRepository.findAll();

        for(Collection collection: result){
            CollectionReadRes collectionReadRes = CollectionReadRes.builder()
                    .id(collection.getId())
                    .collectionTitle(collection.getCollectionTitle())
                    .contentId(collection.getContentInCollect().getId())
                    .contentName(collection.getContentInCollect().getName())
//                    .contentClassify(collection.getContentInCollect().getClassify())
//                    .categoryId(collection.getContentInCollect().getCategoryId().getId())
//                    .writerId(collection.getContentInCollect().getWriterId().getId())
                    .contentImage(collection.getContentInCollect().getContentImages().getFilename())
                    .memberId(collection.getMember().getId())
                    .build();
            collectionReadResList.add(collectionReadRes);
        }
        return collectionReadResList;
    }


//    public CollectionReadRes read(String title) {
////        Optional<Collection> result = collectionRepository.findAllById(id);
//        List<Collection> result = collectionRepository.findAllByCollectionTitle(title);
//        List<Content> contents = new ArrayList<>();
//
//        for (Collection c : result) {
//            contents.add(c.getContentInCollect());
//            //todo 중복 문제 해결해야함 이렇게는 안됨
////            result.remove(c);
//        }
//
//        CollectionReadRes collectionReadRes = CollectionReadRes.builder()
//                .collectionTitle(title)
//                .contentList(contents)
//                .build();
//        return collectionReadRes;
//
//    }

    //todo 검색 그냥 안됨
    @Transactional
    public List<Collection> search(String keyword) {
        List<Collection> postsList = collectionRepository.findByCollectionTitleContaining(keyword);
        return postsList;}



    // todo id 이름 같은 컬렉션 전부 수정
    //  현황 : id 3이면 1~3중 해당은 수정됨 4~~부터는 수정안됨
    public void update(CollectionUpdateReq collectionUpdateReq) {
        Optional<Collection> result = collectionRepository.findById(collectionUpdateReq.getId());

        List<Collection> updateName = collectionRepository.findAll();

        if (result.isPresent()) {

            Collection collection = result.get();
            for (Collection c : updateName) {
                if (c.getCollectionTitle().equals(collection.getCollectionTitle())){
                    c.setCollectionTitle(collectionUpdateReq.getCollectionTitle());
                }
            }
            collection.setCollectionTitle(collectionUpdateReq.getCollectionTitle());

            collectionRepository.save(collection);
        }
    }

    public void delete(Long idx) {
        Optional<Collection> result = collectionRepository.findById(idx);

        if (result.isPresent()) {
            Collection collection = result.get();
            collection.setMember(null);
            collection.setCollectionTitle(null);
            collectionRepository.save(collection);
        }
        collectionRepository.deleteById(idx);
    }




    public void deleteAll(String title) {
        List<Collection> result = collectionRepository.findAllByCollectionTitle(title);

        for (Collection c : result) {
            c.setMember(null);
            c.setCollectionTitle(null);
            collectionRepository.save(c);
            collectionRepository.deleteById(c.getId());
        }
    }

}
