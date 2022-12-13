package com.example.kunuz.controller;

import com.example.kunuz.dto.attach.AttachResponseDTO;
import com.example.kunuz.service.AttachService;
import org.springframework.core.io.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/attach")
public class AttachController {

    private final AttachService service;

    public AttachController(AttachService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        AttachResponseDTO fileName = service.saveToSystem(file);
        return ResponseEntity.ok().body(fileName);
    }


    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
        return service.open(fileName);
    }

    @GetMapping(value = "/open_general/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@PathVariable("fileName") String fileName) {
        return service.open_general(fileName);
    }

    @GetMapping("/download/{fineName}")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
        Resource file = service.download(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<?> deleteById(@PathVariable("fileName") String fileName) {
        String result = service.deleteById(fileName);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/get")
    public ResponseEntity<?> getWithPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Page<AttachResponseDTO> result = service.getWithPage(page, size);
        return ResponseEntity.ok(result);
    }

}
