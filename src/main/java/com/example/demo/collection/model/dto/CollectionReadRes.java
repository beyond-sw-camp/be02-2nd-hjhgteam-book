package com.example.demo.collection.model.dto;

import com.example.demo.content.model.Content;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CollectionReadRes {
    Long id;
    String collectionTitle;
//    List<Content> contentList;

    Long contentId; // 작품id
    String contentName; // 작품 이름
    //    Boolean contentClassify; // 작품 구분(0:웹툰 / 1:웹소설)
//    Long categoryId;
//    String writerId;
    String contentImage;

    Long memberId;





}
