package com.example.demo.collection.model.dto;

import com.example.demo.member.model.Member;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CollectionRes {
    Long id;
    String collectionTitle;
}
