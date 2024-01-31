package com.example.demo.category.controller;

import com.example.demo.category.model.dto.CategoryCreateReq;
import com.example.demo.category.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "카테고리 Controller", tags = "카테고리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {
    private final CategoryService categoryService;

    @ApiOperation(value = "카테고리 추가", notes = "관리자 권한을 가진 관리자가 카테고리를 추가한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(String name) {
        categoryService.create(name);

        return ResponseEntity.ok().body("생성");
    }

    @ApiOperation(value = "카테고리 전체 조회", notes = "카테고리 조회 시 전체 카테고리의 모든 작품을 확인한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(Integer page, Integer size) {

        return ResponseEntity.ok().body(categoryService.list(page, size));
        }

    @ApiOperation(value = "카테고리 검색", notes = "카테고리 id 검색 시 해당 카테고리의 모든 작품을 확인한다.")
    @GetMapping("/{id}")
    public ResponseEntity getProducts(@PathVariable Long id) {
        return ResponseEntity.ok().body(categoryService.read(id));
    }

    @ApiOperation(value = "카테고리 수정", notes = "관리자 권한을 가진 관리자가 카테고리를 수정한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(Long id, String name) {
        categoryService.update(id, name);

        return ResponseEntity.ok().body("수정");
    }

    @ApiOperation(value = "카테고리 삭제", notes = "관리자 권한을 가진 관리자가 카테고리를 삭제한다.")
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok().body("삭제");

    }

}


