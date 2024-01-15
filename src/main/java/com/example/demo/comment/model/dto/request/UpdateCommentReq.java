package com.example.demo.comment.model.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCommentReq {
    Long id;
    String comment;
    Integer rate;
}
