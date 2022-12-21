package com.example.kunuz.controller;

import com.example.kunuz.config.security.CustomUserDetail;
import com.example.kunuz.enums.Language;
import com.example.kunuz.service.ArticleLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/article_like")
@Tag(name = "ArticleLike  Controller")
public class ArticleLikeController {
    private final ArticleLikeService service;


    public ArticleLikeController(ArticleLikeService service) {
        this.service = service;
    }


    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "ArticleLike  like method")
    @PostMapping("/like/{article_id}")
    public ResponseEntity<?> like(@PathVariable("article_id") String articleId,
                                  @RequestHeader(value = "Accept-Language",defaultValue = "RU") Language language) {



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();

        log.info("ArticleLike  like : article id = {}, profile id = {}", articleId, user.getId());

        Boolean result = service.like(articleId, user.getId(), language);
        return ResponseEntity.ok(result);

    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "ArticleLike  dislike method")
    @PostMapping("/dislike/{article_id}")
    public ResponseEntity<?> dislike(@PathVariable("article_id") String articleId,
                                     @RequestHeader(value = "Accept-Language",defaultValue = "RU") Language language) {



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();

        log.info("ArticleLike  dislike : article id = {}, profile id = {}", articleId, user.getId());

        Boolean result = service.dislike(articleId, user.getId(), language);
        return ResponseEntity.ok(result);

    }


    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "ArticleLike  remove method")
    @DeleteMapping("/remove/{article_id}")
    public ResponseEntity<?> remove(@PathVariable("article_id") String articleId,
                                    @RequestHeader(value = "Accept-Language",defaultValue = "RU") Language language) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();

        log.info("ArticleLike  remove : article id = {}, profile id = {}", articleId, user.getId());

        Boolean result = service.remove(articleId, user.getId(),language);
        return ResponseEntity.ok(result);

    }
}
