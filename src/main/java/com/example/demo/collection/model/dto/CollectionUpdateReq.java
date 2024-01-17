package com.example.demo.collection.model.dto;


import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class CollectionUpdateReq {
    @ApiParam(value = "컨렉션의 id값 ", required = true, example = "")
    Long id;
    @ApiParam(value = "컬렉션 이름", required = true, example = "나의 목록")
    String collectionTitle;
}
