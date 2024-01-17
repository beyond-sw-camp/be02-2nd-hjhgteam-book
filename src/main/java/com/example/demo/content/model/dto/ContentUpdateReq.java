package com.example.demo.content.model.dto;


import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContentUpdateReq {

    @ApiParam(value = "작품 번호", required = true, example = "")
    Long id;

    @ApiParam(value = "작가 번호", required = true, example = "")
    Long writerId;

    @ApiParam(value = "카테고리 번호", required = true, example = "")
    Long categoryId;

    @ApiParam(value = "작품 구분", required = true, example = "")
    Boolean classify;

    @ApiParam(value = "작품 이름", required = true, example = "")
    String name;


    @ApiParam(value = "작품 이미지", required = true, example = "")
    String filename;
}
