package com.example.demo.comment.controller;

import com.example.demo.comment.model.dto.request.CommentReq;
import com.example.demo.comment.model.dto.request.UpdateCommentReq;
import com.example.demo.comment.service.CommentService;
import com.example.demo.member.model.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@CrossOrigin("*")
@Api(value = "코멘트 컨트롤러", tags = "코멘트 API")
public class CommentController {
    private final CommentService service;

    @ApiOperation(value = "코멘트 생성")
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity createComment(
            @AuthenticationPrincipal Member member,
                                        @RequestBody CommentReq commentReq) {
        service.createComment(commentReq, member);
        return ResponseEntity.ok().body("ok");
    }
    @ApiOperation(value = "코멘트 전체 조회")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(Integer page, Integer size) {
        return ResponseEntity.ok().body(service.list(page, size));
    }
    @ApiOperation(value = "코멘트 하나 조회")
    @RequestMapping(method = RequestMethod.GET, value = "/read/{id}")
    public ResponseEntity readComment(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.readComment(id));
    }
    @ApiOperation(value = "코멘트 수정")
    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity updateComment(UpdateCommentReq updateCommentReq) {
        service.updateComment(updateCommentReq);
        return ResponseEntity.ok().body("update");
    }
    @ApiOperation(value = "코멘트 삭제")
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity deleteComment(Long id) {
        service.deleteComment(id);
        return ResponseEntity.ok().body("delete");
    }
}
