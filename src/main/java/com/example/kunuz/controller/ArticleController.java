
package com.example.kunuz.controller;

import com.example.kunuz.config.security.CustomUserDetail;
import com.example.kunuz.dto.article.ArticleCreateDTO;
import com.example.kunuz.dto.article.ArticleFilterDTO;
import com.example.kunuz.dto.article.ArticleResponseDTO;
import com.example.kunuz.dto.article.ArticleShortInfoDTO;
import com.example.kunuz.enums.Language;
import com.example.kunuz.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/article")

@Tag(name = "Article controller")
public class ArticleController {

    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }


    @PreAuthorize("hasRole('MODERATOR')")
    @Operation(summary = "Article create method", description = "This method used to create article from moderator")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody ArticleCreateDTO dto) {
        log.info("Article create : title= {} , description = {} , imageId = {}", dto.getTitle(), dto.getDescription(), dto.getImageId());


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail moderator = (CustomUserDetail) authentication.getPrincipal();

        ArticleResponseDTO result = service.create(dto, moderator.getId());

        return ResponseEntity.ok(result);
    }


    @PreAuthorize("hasRole('MODERATOR')")
    @Operation(summary = "Article update method", description = "This method used to update available article all fields")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, ArticleCreateDTO dto,
                                    @RequestHeader("Accept-Language") Language language) {
        log.info("Article update : id = {} , title= {} , description = {} , imageId = {}", id, dto.getTitle(), dto.getDescription(), dto.getImageId());


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail moderator = (CustomUserDetail) authentication.getPrincipal();

        boolean result = service.update(id, dto, moderator.getId(), language);
        return ResponseEntity.ok(result);
    }


    @PreAuthorize("hasRole('MODERATOR')")
    @Operation(summary = "Article delete method", description = "This method used to delete article. Only moderators can delete")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id,
                                    @RequestHeader("Accept-Language") Language language) {
        log.info("Article delete : id = {} ", id);


        boolean result = service.delete(id, language);
        return ResponseEntity.ok(result);
    }




    @PreAuthorize("hasRole('PUBLISHER')")
    @Operation(summary = "Article change status", description = "This method used to change status from NOT_PUBLISHED to PUBlISHED. Only Publisher can it")
    @PutMapping("/change/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id,
                                          @RequestHeader("Accept-Language") Language language) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail publisher = (CustomUserDetail) authentication.getPrincipal();

        log.info("Article change status (publish) : id = {}, publisherId = {}", id, publisher.getId());

        boolean result = service.changeStatus(id, publisher.getId(), language);
        return ResponseEntity.ok(result);
    }


    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Article get last 5 by type id", description = "This method used to getting 5 articles last published by article type id")
    @GetMapping("/get_last5/{type_id}")
    public ResponseEntity<?> getLast5(@PathVariable("type_id") Integer id,
                                      @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {

        log.info("Article get last 5 by article type : typeId = {}", id);

        List<ArticleShortInfoDTO> result = service.getLast5(id, language);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Article get last 3 by type id", description = "This method used to getting 3 articles last published by article type id")
    @GetMapping("/get_last3/{type_id}")
    public ResponseEntity<?> getLast3(@PathVariable("type_id") Integer id,
                                      @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
        log.info("Article get last 3 by article type : typeId = {}", id);

        List<ArticleShortInfoDTO> result = service.getLast3(id, language);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Article get last 8 without given list", description = "This method used to getting 8 articles last published without given article id list")
    @GetMapping("/get_last8")
    public ResponseEntity<?> getLast8(@RequestBody() List<String> list,
                                      @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
        log.info("Article get last 8 without given list :  list {}", list);

        List<ArticleShortInfoDTO> result = service.getLast8(list, language);
        return ResponseEntity.ok(result);
    }


    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Article get by id and lang", description = "This method used to get article by id and lang")
    @GetMapping("/get_by_id/{id}")
    public ResponseEntity<?> getByIdAndLang(@PathVariable("id") String id) {
        log.info("Article get by art and lang : id = {}" + id);

        ArticleResponseDTO result = service.getByIdAndLang(id);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Article get last 4 without given id", description = "This method used to get last 4 article by type id and without given id")
    @GetMapping("/get_last4_by_type1/{type_id}")
    public ResponseEntity<?> getLast4ByType(@Param("id") String id, @PathVariable("type_id") Integer typeId,
                                            @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
        log.info("Article get last 4 by type and without given id : id = {} , typeID = {}", id, typeId);

        List<ArticleShortInfoDTO> result = service.getLast4ByType1(id, typeId, language);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Article get 4 most read articles", description = "This method used to get 4 most read articles")
    @GetMapping("/get_top4")
    public ResponseEntity<?> getTop4(@RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
        log.info("Article get 4 most read articles");
        List<ArticleShortInfoDTO> result = service.getTop4(language);
        return ResponseEntity.ok(result);
    }


    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Article get last 4 by type")
    @GetMapping("/get_last4_by_type2/{type_id}")
    public ResponseEntity<?> getLast4ByType(@PathVariable("type_id") Integer typeId,
                                            @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
        log.info("Article get last 4 by type : typeId = {}", typeId);
        List<ArticleShortInfoDTO> result = service.getLast4ByType2(typeId, language);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Article get last 5 by type id and region key")
    @GetMapping("/get_last5_by_type_and_region")
    public ResponseEntity<?> getLast5ByTypeAdnRegion(@RequestParam("type_id") Integer typeId, @RequestParam("region") String regionKey,
                                                     @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
        log.info("Article get last 5 by type and region : typeId = {} , region key = {} ", typeId, regionKey);
        List<ArticleShortInfoDTO> result = service.getLast5ByTypeAndRegion(typeId, regionKey, language);
        return ResponseEntity.ok(result);
    }


    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Article get list by region key (Page)")
    @GetMapping("/get_list_by_type/{region_key}")
    public ResponseEntity<?> getListByType(@PathVariable("region_key") String regionKey, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        log.info("Article get list by region (Page) : region key {} , page = {} , size = {}", regionKey, page, size);
        Page<ArticleShortInfoDTO> result = service.getListByType(regionKey, page, size);
        return ResponseEntity.ok(result);
    }



    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Article increase view count")
    @PutMapping("/inc_view/{id}")
    public ResponseEntity<?> increaseViewCount(@PathVariable("id") String id,
                                               @RequestHeader("Accept-Language") Language language) {
        log.info("Article increase view count : article id = {}", id);
        Boolean result = service.increaseViewCount(id, language);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Article increase share count")
    @PutMapping("/inc_share/{id}")
    public ResponseEntity<?> increaseShareCount(@PathVariable("id") String id,
                                                @RequestHeader("Accept-Language") Language language) {
        log.info("Article increase share count : article id = {}", id);
        Boolean result = service.increaseShareCount(id, language);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody ArticleFilterDTO filterDTO,
                                    @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Page<ArticleShortInfoDTO> filter = service.filter(filterDTO, page, size);
        return ResponseEntity.ok(filter);
    }


}
