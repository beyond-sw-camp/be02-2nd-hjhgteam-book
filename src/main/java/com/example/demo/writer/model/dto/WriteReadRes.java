package com.example.demo.writer.model.dto;

import com.example.demo.content.model.dto.ContentReadRes;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class WriteReadRes {

    private Long id;
    private String name;
    private List<ContentReadRes> contentReadRes;



}
