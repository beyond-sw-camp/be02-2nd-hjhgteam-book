package com.example.demo.content.repository.querydsl;

import com.example.demo.content.model.Content;
import com.example.demo.content.model.QContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

    public class ContentRepositoryCustomImpl extends QuerydslRepositorySupport implements ContentRepositoryCustom {

    public ContentRepositoryCustomImpl() {
        super(Content.class);
    }

    @Override
    public Page<Content> findList(Pageable pageable) {
        QContent content = new QContent("content");


        List<Content> result = from(content)
                .leftJoin(content.contentImages).fetchJoin()
                .leftJoin(content.categoryId).fetchJoin()
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());

        return new PageImpl<>(result, pageable, pageable.getPageSize());
    }
}
