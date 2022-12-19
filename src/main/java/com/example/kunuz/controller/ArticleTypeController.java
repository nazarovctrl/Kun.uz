package com.example.kunuz.controller;

import com.example.kunuz.dto.article.ArticleTypeShortDTO;
import com.example.kunuz.dto.article.ArticleTypeDTO;
import com.example.kunuz.enums.Language;
import com.example.kunuz.service.ArticleTypeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/article_type")
public class ArticleTypeController {

    private final ArticleTypeService service;

    public ArticleTypeController(ArticleTypeService service) {
        this.service = service;
    }

    @Operation(summary = "ArticleType create method", description = "This method used to create article type (ADMIN)")
    @PostMapping("/create")
    private ResponseEntity<?> create(@RequestBody ArticleTypeDTO dto) {
        log.info("ArticleType create : article type = {} ", dto);

        ArticleTypeDTO result = service.create(dto);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "ArticleType update method")
    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody ArticleTypeDTO dto,
                                     @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {

        log.info("ArticleType create : id = {} , article type = {} ", id, dto);


        ArticleTypeDTO result = service.update(id, dto, language);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "ArticleType delete method")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id,
                                        @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
        log.info("ArticleType delete : id {}", id);

        Boolean result = service.deleteById(id, language);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "ArticleType get list method (Page)")
    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestParam("page") Integer page,
                                     @RequestParam("size") Integer size) {
        log.info("ArticleType get list (page) : page = {}, size = {} ", page, size);

        Page<ArticleTypeDTO> result = service.getList(page, size);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "ArticleType get by lang mehtod")
    @GetMapping("/byLang/{lang}")
    public ResponseEntity<?> getByLang(@PathVariable("lang") String lang) {

        log.info("ArticleType get by lang : lang = {}", lang);
        List<ArticleTypeShortDTO> result = service.getByLang(lang);
        return ResponseEntity.ok(result);
    }


}
