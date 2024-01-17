package com.example.demo.category.service;

import com.example.demo.category.model.Category;
import com.example.demo.category.model.dto.CategoryCreateReq;
import com.example.demo.category.model.dto.CategoryReadRes;
import com.example.demo.category.repository.CategoryRepository;
import com.example.demo.content.model.Content;
import com.example.demo.content.model.dto.ContentReadRes;
import com.example.demo.content.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ContentRepository contentRepository;

    // 추가
    public void create(String name) {
        categoryRepository.save(Category.builder()
                .name(name)
                .build());
    }

    // 전제 조회
    public List<CategoryReadRes> list(Integer page, Integer size){

        Pageable pageable = PageRequest.of(page-1,size);
        Page<Category> result = categoryRepository.findList(pageable);

        List<CategoryReadRes> categoryListResList = new ArrayList<>();

        for (Category category : result.getContent()){
            CategoryReadRes categoryReadRes = read(category.getId());
            categoryListResList.add(categoryReadRes);
        }
            return categoryListResList;
    }


    // id로 조회
    public CategoryReadRes read(Long id) {
        Optional<Category> result = categoryRepository.findById(id);

        if (result.isPresent()) {
            Category category = result.get();

            List<Content> contentList = new ArrayList<>();
            contentList = category.getContentList();

            List<ContentReadRes> categoryReadResList = new ArrayList<>();

            for(Content content : contentList){

                ContentReadRes contentReadRes = ContentReadRes.builder()
                        .id(content.getId())
                        .name(content.getName())
                        .categoryId(content.getCategoryId().getId())
                        .writerId(content.getWriterId().getId())
                        .filename(content.getContentImages().getFilename())
                        .classify(content.getClassify())
                        .build();

                categoryReadResList.add(contentReadRes);

            }

            CategoryReadRes categoryReadRes = CategoryReadRes.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .contentReadRes(categoryReadResList)
                    .build();

            return categoryReadRes;
        }
        return null;
    }

    // 수정
    public void update(Long id, String name){
        Optional<Category> result = categoryRepository.findById(id);

        if(result.isPresent()){
            Category category = result.get();

            if(name != null){
                category.setName(name);
            }
            categoryRepository.save(category);
        }
    }

    // 삭제
    public void delete(Long id){
        Optional<Category> result = categoryRepository.findById(id);

        if(result.isPresent()){
            Category category = result.get();
            List<Content> contentList = category.getContentList();
            for (Content content : category.getContentList()){
                if(content.getCategoryId() == category){
                    content.setCategoryId(null);
                    contentRepository.save(content);
                }
            }
            categoryRepository.deleteById(id);
        }
    }
}
