package com.example.demo.collection.controller;

import com.example.demo.collection.model.Collection;
import com.example.demo.collection.model.dto.CollectionCreateReq;
import com.example.demo.collection.model.dto.CollectionUpdateReq;
import com.example.demo.collection.repository.CollectionRepository;
import com.example.demo.collection.service.CollectionService;
import com.example.demo.member.model.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collection")
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(value = "컬렉션 컨트롤러", tags = "컬렉션 API")
public class CollectionController {
    private final CollectionService collectionService;

    private CollectionRepository collectionRepository;

    @ApiOperation(value = "컬렉션 생성")
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(@AuthenticationPrincipal Member member,
                                 @RequestBody CollectionCreateReq collectionCreateReq) {
        collectionService.create(member, collectionCreateReq);
        return ResponseEntity.ok().body("in");
    }

    @ApiOperation(value = "컬렉션 전체 조회")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list() {
        return ResponseEntity.ok().body(collectionService.list());
    }

    @ApiOperation(value = "컬렉션 상세 조회")
    @GetMapping("/{title}")
    public ResponseEntity getCollection(@PathVariable String title) {
        return ResponseEntity.ok().body(collectionService.read(title));

    }

    //todo 검색
    @GetMapping("/search")
    public ResponseEntity searchItem(@RequestParam("keyword") String keyword, Model model) {
        List<Collection> searchList = collectionService.search(keyword);
        model.addAttribute("searchList", searchList);
        return ResponseEntity.ok().body("");
    }



    @ApiOperation(value = "컬렉션 수정")
    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(@RequestBody CollectionUpdateReq collectionUpdateReq) {
        collectionService.update(collectionUpdateReq);
        return ResponseEntity.ok().body("수정");
    }

    @ApiOperation(value = "컬렉션 요소 중 컨덱트 하나 삭제")
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Long idx) {
        collectionService.delete(idx);
        return ResponseEntity.ok().body("삭제");
    }

    @ApiOperation(value = "컬렉션 요소 전체 삭제")
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteall")
    public ResponseEntity deleteAll(String title) {
        collectionService.deleteAll(title);
        return ResponseEntity.ok().body("삭제");

    }
}
