package com.example.demo.content.cotroller;


import com.example.demo.category.model.Category;
import com.example.demo.content.model.dto.ContentCreateReq;
import com.example.demo.content.model.dto.ContentCreateRes;
import com.example.demo.content.model.dto.ContentUpdateReq;
import com.example.demo.content.service.ContentService;
import com.example.demo.writer.model.Writer;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@Api(value = "작품 Controller", tags = "작품 API")
@RestController
@RequestMapping("/content")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ContentController {

    private final ContentService contentService;

    @ApiOperation(value = "작품 추가", notes = "관리자 권한을 가진 관리자가 작품을 추가한다.")
    @PostMapping(value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity create(
//            @RequestPart ContentCreateReq contentCreateReq,
            ContentCreateReq contentCreateReq,
            @RequestPart MultipartFile uploadFiles
    ) {
        ContentCreateRes response = contentService.create(contentCreateReq, uploadFiles);

        return ResponseEntity.ok().body(response);

    }

    @ApiOperation(value = "작품 전체 조회", notes = "모든 작품을 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(Integer page, Integer size) {

        return ResponseEntity.ok().body(contentService.list(page, size));
    }

    @ApiOperation(value = "작품 id로 검색", notes = "작품 id로 작품을 검색한다.")
    @ApiParam(name = "작품 id", required = true, example = "")
    @GetMapping("/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        return ResponseEntity.ok().body(contentService.read(id));
    }

    @ApiOperation(value = "작품 이름으로 검색", notes = "작품 이름으로 작품을 검색한다.")
    @ApiParam(name = "작품 이름", required = true, example = "")
    @PostMapping("/readByName")
    public ResponseEntity readByName(@RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");
        return ResponseEntity.ok().body(contentService.readByName(name));
    }

    @ApiOperation(value = "작품 수정", notes = "관리자 권한을 가진 관리자가 작품을 수정한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(ContentUpdateReq contentUpdateReq,
                                 MultipartFile uploadFiles) {
        contentService.update(contentUpdateReq, uploadFiles);

        return ResponseEntity.ok().body("수정");
    }


    @ApiOperation(value = "작품 삭제", notes = "관리자 권한을 가진 관리자가 작품을 삭제한다.")
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Long id) {
        contentService.delete(id);
        return ResponseEntity.ok().body("삭제");

    }


}
