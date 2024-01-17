package com.example.demo.collection.model.dto;


import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CollectionCreateReq {
    @ApiParam(value = "컬렉션 이름", required = true, example = "나의 목록")
    String collectionTitle;
    @ApiParam(value = "컨텍트의 id값 ", required = true, example = "")
    Long contentIdx;
}
