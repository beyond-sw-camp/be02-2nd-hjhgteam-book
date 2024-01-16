package com.example.demo.comment.model.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentReq {
    String comment;
    Integer rate;
    Long contentId;
}
