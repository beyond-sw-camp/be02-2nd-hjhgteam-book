package com.example.demo.collection.model.dto;


import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class CollectionUpdateReq {
    Long id;
    String collectionTitle;
}
