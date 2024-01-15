package com.example.demo.comment.model.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;

@Getter
@Builder
public class ListCommentReq {

    private String comment;

    private Integer rate;
}
