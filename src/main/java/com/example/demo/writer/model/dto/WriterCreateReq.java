package com.example.demo.writer.model.dto;

import io.swagger.annotations.ApiParam;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WriterCreateReq {

    @ApiParam(name = "작가 이름" , required = true, example = "")
    private String name;
}
