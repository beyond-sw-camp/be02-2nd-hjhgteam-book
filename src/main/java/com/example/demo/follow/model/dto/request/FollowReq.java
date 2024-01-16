package com.example.demo.follow.model.dto.request;


import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FollowReq {
    @ApiParam(value = "팔로우하는 사람(로그인한 사람)의 회원 ID - 받아온다고 가정", required = true, example = "1")
    private Long follower_id;
    @ApiParam(value = "팔로우할 사람(로그인한 사람)의 회원 ID", required = true, example = "2")
    private Long following_id;
}
