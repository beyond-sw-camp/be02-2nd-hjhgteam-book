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
@Api(value = "컬렉션 Controller", tags = "컬렉션 API")
public class CollectionController {
    private final CollectionService collectionService;

    private CollectionRepository collectionRepository;

    @ApiOperation(value = "컬렉션 생성", notes = "사용자들이 각각의 컬렉션에 컨텐트를 추가한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(@AuthenticationPrincipal Member member,
                                 @RequestBody CollectionCreateReq collectionCreateReq) {
        collectionService.create(member, collectionCreateReq);
        return ResponseEntity.ok().body("in");
    }

    @ApiOperation(value = "컬렉션 전체 조회", notes = "모든 컬렉션을 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list() {
        return ResponseEntity.ok().body(collectionService.list());
    }

//    @ApiOperation(value = "컬렉션 상세 조회", notes = "컬렌션 title로 컬렉션을 조회한다.")
//    @GetMapping("/detail/{title}")
//    public ResponseEntity getCollection(@PathVariable String title) {
//        return ResponseEntity.ok().body(collectionService.read(title));
//    }

    //todo 검색
//    @ApiOperation(value = "컬렉션 검색", notes = "컬렌션에서 작품, 유저등으로 컬렉션을 검색한다.")
    @GetMapping("/search")
    public ResponseEntity searchItem(@RequestParam("keyword") String keyword, Model model) {
        List<Collection> searchList = collectionService.search(keyword);
        model.addAttribute("searchList", searchList);
        return ResponseEntity.ok().body("");
    }


    @ApiOperation(value = "컬렉션 이름 수정", notes = "컬렌션의 title을 변경한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(@RequestBody CollectionUpdateReq collectionUpdateReq) {
        collectionService.update(collectionUpdateReq);
        return ResponseEntity.ok().body("수정");
    }

    @ApiOperation(value = "컬렉션 요소 중 컨텐트 하나 삭제", notes = "컬렌션의 컨텍트를 한개 삭제한다.")
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Long idx) {
        collectionService.delete(idx);
        return ResponseEntity.ok().body("삭제");
    }

    @ApiOperation(value = "컬렉션 요소 중 컨텐트 전체 삭제", notes = "컬렌션의 컨텍트를 전체 삭제한다.")
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteall")
    public ResponseEntity deleteAll(String title) {
        collectionService.deleteAll(title);
        return ResponseEntity.ok().body("삭제");

    }
}
