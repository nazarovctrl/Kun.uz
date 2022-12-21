package com.example.kunuz.controller;

import com.example.kunuz.config.security.CustomUserDetail;
import com.example.kunuz.dto.article.SavedArticleResponseDTO;
import com.example.kunuz.enums.Language;
import com.example.kunuz.service.SavedArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/saved_article")
public class SavedArticleController {

    private final SavedArticleService service;

    public SavedArticleController(SavedArticleService service) {
        this.service = service;
    }



    @PreAuthorize("hasRole('USER')")
    @PostMapping("/save/{article_id}")
    public ResponseEntity<?> create(@PathVariable("article_id") String articleId)  {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();

        Boolean result = service.create(articleId, user.getId());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{article_id}")
    public ResponseEntity<?> delete(@PathVariable("article_id") String articleId)  {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();

        Boolean result = service.delete(articleId, user.getId());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestHeader(value = "Accept-Language",defaultValue = "RU") Language language) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();

        List<SavedArticleResponseDTO> result = service.getList(user.getId(),language);
        return ResponseEntity.ok(result);
    }

}
