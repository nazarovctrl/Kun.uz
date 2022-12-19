package com.example.kunuz.controller;

import com.example.kunuz.dto.EmailHistoryResponseDTO;
import com.example.kunuz.service.EmailHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email_history")
public class EmailHistoryController {

    private final EmailHistoryService service;

    public EmailHistoryController(EmailHistoryService service) {
        this.service = service;
    }

    @GetMapping("/get_by_email")
    public ResponseEntity<?> getByEmail(@RequestBody EmailHistoryResponseDTO dto) {
        List<EmailHistoryResponseDTO> result = service.getByEmail(dto.getEmail());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<?> getByDate(@PathVariable("date") String date) {
        List<EmailHistoryResponseDTO> result = service.getByDate(date);
        return ResponseEntity.ok(result);

    }


    @GetMapping("/get_list")
    public ResponseEntity<?> getList(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Page<EmailHistoryResponseDTO> result = service.getList(page, size);
        return ResponseEntity.ok(result);

    }

}
