package com.example.demo.follow.controller;

import com.example.demo.follow.service.FollowService;
import com.example.demo.member.model.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
@CrossOrigin("*")
@Api(value = "팔로우 컨트롤러", tags = "팔로우 API")
public class FollowController {
    private final FollowService followService;

    @ApiOperation("팔로우/언팔로우")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity follow(@AuthenticationPrincipal Member member, Long following_id) {
        followService.followMember(member.getId(),following_id);
        return ResponseEntity.ok().body("ok");
    }

    @ApiOperation("팔로워 목록 확인")
    @RequestMapping(method = RequestMethod.GET, value = "/list/followers")
    public ResponseEntity followers(@AuthenticationPrincipal Member member) {

        return ResponseEntity.ok().body(followService.showFollowers(member.getId()));
    }

    @ApiOperation("팔로잉 목록 확인")
    @RequestMapping(method = RequestMethod.GET, value = "/list/followings")
    public ResponseEntity followings(@AuthenticationPrincipal Member member) {

        return ResponseEntity.ok().body(followService.showFollowings(member.getId()));
    }}