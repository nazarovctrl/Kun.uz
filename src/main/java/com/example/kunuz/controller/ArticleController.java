package com.example.kunuz.controller;

import com.example.kunuz.dto.article.ArticleCreateDTO;
import com.example.kunuz.dto.article.ArticleResponseDTO;
import com.example.kunuz.dto.article.ArticleShortInfoDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.ArticleService;
import com.example.kunuz.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @PostMapping("/admin/create")
    public ResponseEntity<?> create(@Valid @RequestBody ArticleCreateDTO dto, HttpServletRequest request) {
        log.info(dto.toString());

        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.MODERATOR);
        ArticleResponseDTO result = service.create(dto, profileId);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, ArticleCreateDTO dto, HttpServletRequest request) {
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.MODERATOR);
        boolean result = service.update(id, dto, profileId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id, HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.MODERATOR);
        boolean result = service.delete(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/admin/change/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id, HttpServletRequest request) {
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.PUBLISHER);
        boolean result = service.changeStatus(id, profileId);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/user/get_last5/{type_id}")
    public ResponseEntity<?> getLast5(@PathVariable("type_id") Integer id) {
        List<ArticleShortInfoDTO> result = service.getLast5(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/get_last3/{type_id}")
    public ResponseEntity<?> getLast3(@PathVariable("type_id") Integer id) {
        List<ArticleShortInfoDTO> result = service.getLast3(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/get_last8")
    public ResponseEntity<?> getLast3(@RequestBody() List<String> list) {
        List<ArticleShortInfoDTO> result = service.getLast8(list);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/user/get_by_id/{id}")
    public ResponseEntity<?> getByIdANdLang(@PathVariable("id") String id) {
        ArticleResponseDTO result = service.getByIdAndLang(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("user/get_last4_by_type1/{type_id}")
    public ResponseEntity<?> getLast4ByType(@Param("id") String id, @PathVariable("type_id") Integer typeId) {
        List<ArticleShortInfoDTO> result = service.getLast4ByType1(id, typeId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("user/gettop4")
    public ResponseEntity<?> getTop4() {
        List<ArticleShortInfoDTO> result = service.getTop4();
        return ResponseEntity.ok(result);
    }

    @GetMapping("user/get_last4_by_type2/{type_id}")
    public ResponseEntity<?> getLast4ByType(@PathVariable("type_id") Integer typeId) {
        List<ArticleShortInfoDTO> result = service.getLast4ByType2(typeId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("user/get_last5_by_type_and_region")
    public ResponseEntity<?> getLast5ByTypeAdnRegion(@RequestParam("type_id") Integer typeId, @RequestParam("region") String regionKey) {
        List<ArticleShortInfoDTO> result = service.getLast5ByTypeAndRegion(typeId, regionKey);
        return ResponseEntity.ok(result);
    }

    @GetMapping("user/get_list_by_type/{region_key}")
    public ResponseEntity<?> getListByType(@PathVariable("region_key") String regionKey, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Page<ArticleShortInfoDTO> result = service.getListByType(regionKey, page, size);
        return ResponseEntity.ok(result);
    }


}
