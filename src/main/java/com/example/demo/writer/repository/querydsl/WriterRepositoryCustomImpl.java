package com.example.demo.writer.repository.querydsl;

import com.example.demo.category.model.QCategory;
import com.example.demo.content.model.QContent;
import com.example.demo.content.model.QContentImage;
import com.example.demo.writer.model.QWriter;
import com.example.demo.writer.model.Writer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class WriterRepositoryCustomImpl extends QuerydslRepositorySupport implements WriterRepositoryCustom {
    public WriterRepositoryCustomImpl() {
        super(Writer.class);
    }

    @Override
    public Page<Writer> findList(Pageable pageable) {
        QWriter writer = new QWriter("writer");
        QCategory category = new QCategory(QCategory.category);
        QContent content = new QContent(QContent.content);
        QContentImage contentImage = new QContentImage(QContentImage.contentImage);


        List<Writer> result = from(writer)
                .leftJoin(writer.contentList, content).fetchJoin()
                .leftJoin(content.contentImages, contentImage).fetchJoin()
                .leftJoin(content.categoryId, category).fetchJoin()
                .where(writer.id.eq(writer.id))
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

