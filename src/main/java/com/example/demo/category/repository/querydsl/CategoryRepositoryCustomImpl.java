package com.example.demo.category.repository.querydsl;

import com.example.demo.category.model.Category;
import com.example.demo.category.model.QCategory;
import com.example.demo.content.model.Content;
import com.example.demo.content.model.QContent;
import com.example.demo.content.model.QContentImage;

import com.example.demo.writer.model.QWriter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryRepositoryCustomImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom {
    public CategoryRepositoryCustomImpl() {
        super(Content.class);
    }

    @Override
    public Page<Category> findList(Pageable pageable) {
        QCategory category = new QCategory("category");
        QContent content = new QContent(QContent.content);
        QContentImage contentImage = new QContentImage(QContentImage.contentImage);
        QWriter writer = new QWriter(QWriter.writer);


        List<Category> result = from(category)
                .leftJoin(category.contentList, content).fetchJoin()
                .leftJoin(content.contentImages, contentImage).fetchJoin()
                .leftJoin(content.writerId, writer).fetchJoin()
                .where(category.id.eq(category.id))
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());

    // select * from category
        //left join content
        //on category.id = content.category_id
        //left join content_image
        //on content.id = content_image.content_id
        //left join writer
        //on content.writer_id = writer.id
        //where category.id=2;
        return new PageImpl<>(result, pageable, pageable.getPageSize());
    }
}

