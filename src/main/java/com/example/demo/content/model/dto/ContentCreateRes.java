package com.example.demo.content.model.dto;

import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ContentCreateRes {

    @ApiParam(value = "작품 이름", required = true, example = "")
    private String name;

    @ApiParam(value = "작품 구분", required = true, example = "0:웹툰, 1:웹소설")
    private Boolean classify;

    @ApiParam(value = "작품 이미지", required = true, example = "")
    private String filename;

}
