package com.example.demo.comment.repository;

import com.example.demo.comment.model.Comment;

import com.example.demo.comment.model.QComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class CommentRepositoryCustomImpl extends QuerydslRepositorySupport implements CommentRepositoryCustom {
    public CommentRepositoryCustomImpl() {
        super(Comment.class);
    }

    @Override
    public Page<Comment> findList(Pageable pageable) {
        QComment comment = new QComment("comment");


        List<Comment> result = from(comment)
                .leftJoin(comment.member).fetchJoin()
                .leftJoin(comment.content).fetchJoin()
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());

        return new PageImpl<>(result, pageable, pageable.getPageSize());
    }
}
