package com.example.demo.comment.model.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class ContentCommentRes {
    private Long contentId;
    private String contentName;
    private String contentImage;
    private Boolean contentClassify;
}
