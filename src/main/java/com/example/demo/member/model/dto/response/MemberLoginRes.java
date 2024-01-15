package com.example.demo.member.model.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel(value = "로그인 응답")
public class MemberLoginRes {
    @ApiParam(value = "JWT 반환")
    String token;
}
