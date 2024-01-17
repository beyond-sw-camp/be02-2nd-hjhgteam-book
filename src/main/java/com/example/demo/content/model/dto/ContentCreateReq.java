package com.example.demo.content.model.dto;

import com.example.demo.category.model.Category;
import com.example.demo.writer.model.Writer;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Cache;

@Builder
@Getter
@Setter
public class ContentCreateReq {

    @ApiParam(value = "작품 이름", required = true, example = "")
    private String name;

    @ApiParam(value = "작품 구분", required = true, example = "0:웹툰, 1:웹소설")
    private Boolean classify;

    private Writer writer_id;
    private Category category_id;


}
