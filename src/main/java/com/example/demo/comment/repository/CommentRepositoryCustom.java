package com.example.demo.comment.repository;
import com.example.demo.comment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom {
    public Page<Comment> findList(Pageable pageable);
}
