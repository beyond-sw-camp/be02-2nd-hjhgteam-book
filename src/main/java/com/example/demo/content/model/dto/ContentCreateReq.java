package com.example.demo.content.model.dto;

import com.example.demo.category.model.Category;
import com.example.demo.category.model.dto.CategoryContentCreateReq;
import com.example.demo.writer.model.Writer;
import com.example.demo.writer.model.dto.WriterContentCreateReq;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Cache;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
public class ContentCreateReq {

    @ApiParam(value = "작품 이름", required = true, example = "")
    private String name;

    @ApiParam(value = "작품 구분 // 0:웹툰, 1:웹소설", required = true, example = "0:웹툰, 1:웹소설")
    private Boolean classify;

    private Long writer_id;
    private Long category_id;
}
