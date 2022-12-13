package com.example.kunuz.controller;

import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.ArticleLikeService;
import com.example.kunuz.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article_like")
public class ArticleLikeController {
    private final ArticleLikeService service;


    public ArticleLikeController(ArticleLikeService service) {
        this.service = service;
    }


    @PostMapping("/like/{article_id}")
    public ResponseEntity<?> like(@PathVariable("article_id") String articleId, HttpServletRequest request) {

        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.USER);

        Boolean result = service.like(articleId, profileId);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/dislike/{article_id}")
    public ResponseEntity<?> dislike(@PathVariable("article_id") String articleId, HttpServletRequest request) {

        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.USER);

        Boolean result = service.dislike(articleId, profileId);
        return ResponseEntity.ok(result);

    }

    @DeleteMapping("/remove/{article_id}")
    public ResponseEntity<?> remove(@PathVariable("article_id") String articleId, HttpServletRequest request) {

        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.USER);

        Boolean result = service.remove(articleId, profileId);
        return ResponseEntity.ok(result);

    }
}
