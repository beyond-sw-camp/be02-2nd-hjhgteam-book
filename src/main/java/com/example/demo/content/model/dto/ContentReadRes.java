package com.example.demo.content.model.dto;

import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContentReadRes {

    private Long categoryId;

    private Long writerId;

    private Boolean classify;

    private Long id;

    private String name;

    private String filename;

    private String categoryName;
    private String writerName;





}
