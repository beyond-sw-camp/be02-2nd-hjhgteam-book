package com.example.demo.comment.controller;

import com.example.demo.comment.model.dto.request.CommentReq;
import com.example.demo.comment.model.dto.request.UpdateCommentReq;
import com.example.demo.comment.service.CommentService;
import com.example.demo.member.model.Member;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Api(value = "코멘트 컨트롤러", tags = "코멘트 API")
public class CommentController {
    private final CommentService service;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity createComment(@AuthenticationPrincipal Member member,
                                        CommentReq commentReq, Long contentId) {
        service.createComment(commentReq, member, contentId);
        return ResponseEntity.ok().body("ok");
    }
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(Integer page, Integer size) {
        return ResponseEntity.ok().body(service.list(page, size));
    }
    @RequestMapping(method = RequestMethod.GET, value = "/read/{id}")
    public ResponseEntity readComment(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.readComment(id));
    }
    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity updateComment(UpdateCommentReq updateCommentReq) {
        service.updateComment(updateCommentReq);
        return ResponseEntity.ok().body("update");
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity deleteComment(Long id) {
        service.deleteComment(id);
        return ResponseEntity.ok().body("delete");
    }
}
