package com.example.demo.collection.model.dto;

import com.example.demo.member.model.Member;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CollectionListRes {
    List<CollectionRes> resultList;

}
