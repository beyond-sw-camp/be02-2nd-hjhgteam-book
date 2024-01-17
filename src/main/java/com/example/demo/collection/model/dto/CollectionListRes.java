package com.example.demo.collection.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CollectionListRes {
    List<CollectionReadRes> resultList;

}
