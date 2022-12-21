package com.example.kunuz.controller;

import com.example.kunuz.config.security.CustomUserDetail;
import com.example.kunuz.service.CommentLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment_like")
public class CommentLikeController {
    private final CommentLikeService service;

    public CommentLikeController(CommentLikeService service) {
        this.service = service;
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/like/{c_id}")
    public ResponseEntity<?> like(@PathVariable("c_id") Integer id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();

        Boolean result = service.like(id, user.getId());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/dislike/{c_id}")
    public ResponseEntity<?> dislike(@PathVariable("c_id") Integer id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
        boolean result = service.dislike(id, user.getId());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/remove/{c_id}")
    public ResponseEntity<?> remove(@PathVariable("c_id") Integer id) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();

        boolean result = service.remove(id, user.getId());
        return ResponseEntity.ok(result);
    }

}
