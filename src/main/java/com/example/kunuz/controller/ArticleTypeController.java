package com.example.kunuz.controller;

import com.example.kunuz.dto.article.ArticleTypeShortDTO;
import com.example.kunuz.dto.article.ArticleTypeDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.ArticleTypeService;
import com.example.kunuz.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article_type")
public class ArticleTypeController {

    private final ArticleTypeService service;

    public ArticleTypeController(ArticleTypeService service) {
        this.service = service;
    }

    @PostMapping
    private ResponseEntity<?> create(@RequestBody ArticleTypeDTO dto, HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);

        ArticleTypeDTO result = service.create(dto);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody ArticleTypeDTO dto,
                                     HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        ArticleTypeDTO result = service.update(id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id,
                                        @RequestHeader("Authorization") String token,
                                        HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);

        Boolean result = service.deleteById(id);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestParam("page") Integer page,
                                     @RequestParam("size") Integer size,
                                     HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        Page<ArticleTypeDTO> result = service.getList(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/byLang/{lang}")
    public ResponseEntity<?> getByLang(@PathVariable("lang") String lang,
                                       HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        List<ArticleTypeShortDTO> result = service.getByLang(lang);
        return ResponseEntity.ok(result);
    }


}
