package com.example.demo.collection.controller;

import com.example.demo.collection.model.Collection;
import com.example.demo.collection.model.dto.CollectionCreateReq;
import com.example.demo.collection.model.dto.CollectionUpdateReq;
import com.example.demo.collection.service.CollectionService;
import com.example.demo.member.model.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collection")
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(value = "컬렉션 컨트롤러", tags = "컬렉션 API")
public class CollectionController {
    private final CollectionService collectionService;

    @ApiOperation(value = "컬렉션 생성")
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(@AuthenticationPrincipal Member member,
                                 @RequestPart CollectionCreateReq collectionCreateReq) {
        return ResponseEntity.ok().body(collectionService.create(member, collectionCreateReq));
    }

    @ApiOperation(value = "컬렉션 전체 조회")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
    Pageable pageable, String searchKeyword) {



        Page<Collection> list = null;

        if(searchKeyword == null){
            list = collectionService.collectionList(pageable);
        }else{
            list = collectionService.collectionSearchList(searchKeyword, pageable);
        }


        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list" , list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return ResponseEntity.ok().body(collectionService.list());
    }

    @ApiOperation(value = "컬렉션 하나 조회")
    @GetMapping("/{idx}")
    public ResponseEntity getCollection(@PathVariable Long idx) {
        return ResponseEntity.ok().body(collectionService.read(idx));

    }

    @ApiOperation(value = "컬렉션 수정")
    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(CollectionUpdateReq collectionUpdateReq) {
        collectionService.update(collectionUpdateReq);
        return ResponseEntity.ok().body("수정");
    }

    @ApiOperation(value = "컬렉션 삭제")
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Long id) {
        collectionService.delete(id);
        return ResponseEntity.ok().body("삭제");

    }
}
