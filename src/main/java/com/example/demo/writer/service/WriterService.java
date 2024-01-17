package com.example.demo.writer.service;


import com.example.demo.content.model.Content;
import com.example.demo.content.model.dto.ContentReadRes;
import com.example.demo.writer.model.Writer;
import com.example.demo.writer.model.dto.WriteReadRes;
import com.example.demo.writer.repository.WriterRepository;
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
public class WriterService {

    private final WriterRepository writerRepository;

    // 추가
    public void create(String name) {
        writerRepository.save(Writer.builder()
                .name(name)
                .build());
    }

    // 전체 조회
    public List<WriteReadRes> list(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Writer> result = writerRepository.findList(pageable);

        List<WriteReadRes> writeReadResList = new ArrayList<>();

        for (Writer writer : result.getContent()) {
            WriteReadRes writeReadRes2 = read(writer.getId());

            writeReadResList.add(writeReadRes2);
        }
        return writeReadResList;
    }

    // id로 조회
    public WriteReadRes read(Long id) {
        Optional<Writer> result = writerRepository.findById(id);

        if (result.isPresent()) {
            Writer writer = result.get();

            List<Content> contentList = new ArrayList<>();
            contentList = writer.getContentList();

            List<ContentReadRes> contentReadResList = new ArrayList<>();

            for (Content content : contentList) {
                ContentReadRes contentReadRes = ContentReadRes.builder()
                        .id(content.getId())
                        .name(content.getName())
                        .categoryId(content.getCategoryId().getId())
                        .writerId(content.getWriterId().getId())
                        .filename(content.getContentImages().getFilename())
                        .classify(content.getClassify())
                        .build();

                contentReadResList.add(contentReadRes);
            }

            WriteReadRes writeReadRes = WriteReadRes.builder()
                    .id(writer.getId())
                    .name(writer.getName())
                    .contentReadRes(contentReadResList)
                    .build();

            return writeReadRes;
        }
        return null;
    }

    // 작가 수정
    public void update(Long id, String name) {
        Optional<Writer> result = writerRepository.findById(id);

        if (result.isPresent()) {
            Writer writer = result.get();

            if (name != null) {
                writer.setName(name);
            }
            writerRepository.save(writer);
        }
    }

    // 삭제
    public void delete(Long id){
        Optional<Writer> result = writerRepository.findById(id);

        if(result.isPresent()){
            Writer writer = result.get();
            List<Content> contentList = writer.getContentList();
            for (Content content : writer.getContentList()){
                if(content.getWriterId() == writer){
                    content.setWriterId(null);
                    writerRepository.save(writer);
                }
            }
            writerRepository.deleteById(id);
        }
    }

}
