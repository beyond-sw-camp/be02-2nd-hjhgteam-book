package com.example.demo.writer.controller;

import com.example.demo.writer.service.WriterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "작가 Controller", tags = "작가 API")
@RestController
@RequestMapping("/writer")
@RequiredArgsConstructor
@CrossOrigin("*")
public class WriteController {

    private final WriterService writerService;

    @ApiOperation(value = "작가 추가", notes = "관리자 권한을 가진 관리자가 작가를 추가한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(String name){
        writerService.create(name);

        return ResponseEntity.ok().body("생성");
    }

    @ApiOperation(value = "작가 전체 조회", notes = "전체 작가 조회 시 전체 카테고리의 모든 작품을 확인한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(Integer page, Integer size) {

        return ResponseEntity.ok().body(writerService.list(page, size));
    }

    @ApiOperation(value = "작가 검색", notes = "작가 id로 검색 시 해당 작가 모든 작품을 확인한다.")
    @GetMapping("/{id}")
    public ResponseEntity getProducts(@PathVariable Long id) {
        return ResponseEntity.ok().body(writerService.read(id));
    }

    @ApiOperation(value = "작가 수정", notes = "관리자 권한을 가진 관리자가 작가를 수정한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(Long id, String name) {
        writerService.update(id, name);

        return ResponseEntity.ok().body("수정");
    }

    @ApiOperation(value = "작가 삭제", notes = "관리자 권한을 가진 관리자가 작가를 삭제한다.")
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Long id) {
        writerService.delete(id);
        return ResponseEntity.ok().body("삭제");

    }



}
