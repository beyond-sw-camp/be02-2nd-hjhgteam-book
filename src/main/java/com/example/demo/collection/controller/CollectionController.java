package com.example.demo.collection.controller;

import com.example.demo.collection.model.Collection;
import com.example.demo.collection.model.dto.CollectionCreateReq;
import com.example.demo.collection.model.dto.CollectionListRes;
import com.example.demo.collection.model.dto.CollectionUpdateReq;
import com.example.demo.collection.service.CollectionService;
import com.example.demo.member.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/collection")
@CrossOrigin("*")
public class CollectionController {
    CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(@AuthenticationPrincipal Member member,
                                 @RequestPart CollectionCreateReq collectionCreateReq) {
        return ResponseEntity.ok().body(collectionService.create(member, collectionCreateReq));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
    Pageable pageable, String searchKeyword) {



        Page<Collection> list = null;
        //searchKeyword = 검색하는 단어*/
        if(searchKeyword == null){
            list = collectionService.collectionList(pageable); //기존의 리스트보여줌
        }else{
            list = collectionService.collectionSearchList(searchKeyword, pageable); //검색리스트반환
        }


        int nowPage = list.getPageable().getPageNumber() + 1; //pageable에서 넘어온 현재페이지를 가지고올수있다 * 0부터시작하니까 +1
        int startPage = Math.max(nowPage - 4, 1); //매개변수로 들어온 두 값을 비교해서 큰값을 반환
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list" , list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return ResponseEntity.ok().body(collectionService.list());
    }

//    //작품명으로

//    //닉네임으로





    @GetMapping("/{idx}")
    public ResponseEntity getCollection(@PathVariable Long idx) {
        return ResponseEntity.ok().body(collectionService.read(idx));

    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(CollectionUpdateReq collectionUpdateReq) {
        collectionService.update(collectionUpdateReq);
        return ResponseEntity.ok().body("수정");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Long id) {
        collectionService.delete(id);
        return ResponseEntity.ok().body("삭제");

    }
}
