package com.example.demo.category.model.dto;

import io.swagger.annotations.ApiParam;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateReq {

    @ApiParam(name = "카테고리 이름" , required = true, example = "")
    private String name;
}
