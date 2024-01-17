package com.example.demo.collection.repository.querydsl;

import com.example.demo.collection.model.Collection;
import com.example.demo.collection.model.QCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CollectionRepositoryCustomImpl extends QuerydslRepositorySupport implements CollectionRepositoryCustom {

    public CollectionRepositoryCustomImpl() {
        super(Collection.class);
    }

    @Override
    public Page<Collection> findList(Pageable pageable) {
        QCollection collection = new QCollection("collection");


//        // 조인이 필요한 각 클래스들에 대한 객체 생성
//        QProduct product = new QProduct("product");
//        QProductImage productImage = new QProductImage("productImage");
//        QMember member = new QMember("member");
//
//        // QueryDSL 을 사용하기 위한 from 메서드 작성
//        List<Product> result = from(product)
//                .leftJoin(product.productImageList, productImage).fetchJoin()
//                .leftJoin(product.member, member).fetchJoin()
//                // 중복제거를 위한 코드 추가
//                .fetch().stream().distinct().collect(Collectors.toList());
//
//        return result;

        return null;
    }
}

