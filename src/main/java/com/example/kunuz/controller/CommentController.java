package com.example.kunuz.controller;

import com.example.kunuz.config.security.CustomUserDetail;
import com.example.kunuz.dto.comment.CommentCreateDTO;
import com.example.kunuz.dto.comment.CommentResponseDTO;
import com.example.kunuz.dto.comment.CommentUpdateDTO;
import com.example.kunuz.enums.Language;
import com.example.kunuz.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comment")
@Tag(name = "Comment controller", description = "This controller worked with comments")
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }


    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Method for create comment", description = "This method used to create comment")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CommentCreateDTO dto) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();

        log.info("Create comment profileId = " + user.getId() + " " + dto);

        CommentResponseDTO result = service.create(dto, user.getId());
        return ResponseEntity.ok(result);
    }


    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Method for update comment", description = "This method used to update comment")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody CommentUpdateDTO dto,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();

        Boolean result = service.update(dto, user.getId(), language);

        return ResponseEntity.ok(result);
    }



    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Method for delete comment", description = "This method used to delete comment")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();

        Boolean result = service.delete(id, user.getUser(), language);
        return ResponseEntity.ok(result);

    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Method for get article's comment list ", description = "This method used to getting  article's comment list")
    @GetMapping("/get/{article_id}")
    public ResponseEntity<?> getListByArticleId(@PathVariable("article_id") String articleId) {
        List<CommentResponseDTO> result = service.getListByArticleId(articleId);
        return ResponseEntity.ok(result);
    }


    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Method for getting all comment list", description = "This method used getting all comment list with pagination")
    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Page<CommentResponseDTO> result = service.getPageList(page, size);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Method for getting comment reply comment list", description = "This method used getting comment  reply comment list ")
    @GetMapping("/get_reply/{id}")
    public ResponseEntity<?> getReplyList(@PathVariable("id") Integer id) {
        List<CommentResponseDTO> result = service.getReplyList(id);
        return ResponseEntity.ok(result);
    }


}
