package com.example.demo.comment.model.dto.response;

import com.example.demo.content.model.Content;
import com.example.demo.member.model.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentRes {
    private Long id;
    private String comment;
    private Integer rate;
    private Member member;
    private Long contentId;
}
