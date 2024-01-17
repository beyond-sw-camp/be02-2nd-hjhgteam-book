package com.example.demo.category.model.dto;

import com.example.demo.content.model.dto.ContentReadRes;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CategoryReadRes {

    private Long id;
    private String name;

    private List<ContentReadRes> contentReadRes;



}
