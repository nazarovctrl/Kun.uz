package com.example.kunuz.controller;

import com.example.kunuz.dto.region.RegionDTO;
import com.example.kunuz.dto.region.RegionShortDTO;
import com.example.kunuz.service.RegionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    private final RegionService service;


    public RegionController(RegionService service) {
        this.service = service;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody RegionDTO dto) {

        RegionDTO result = service.create(dto);
        return ResponseEntity.ok(result);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody RegionDTO dto) {

        RegionDTO result = service.update(id, dto);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {

        Boolean result = service.deleteById(id);
        return ResponseEntity.ok(result);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestParam("page") Integer page,
                                     @RequestParam("size") Integer size) {

        Page<RegionDTO> result = service.getList(page, size);
        return ResponseEntity.ok(result);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/byLang/{lang}")
    public ResponseEntity<?> getByLang(@PathVariable("lang") String lang) {

        List<RegionShortDTO> result = service.getByLang(lang);
        return ResponseEntity.ok(result);
    }

}
