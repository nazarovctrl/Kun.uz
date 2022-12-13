package com.example.kunuz.controller;

import com.example.kunuz.dto.region.RegionDTO;
import com.example.kunuz.dto.region.RegionShortDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.RegionService;
import com.example.kunuz.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    private final RegionService service;


    public RegionController(RegionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RegionDTO dto,
                                    HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        RegionDTO result = service.create(dto);

        return ResponseEntity.ok(result);
    }


    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody RegionDTO dto,
                                     HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        RegionDTO result = service.update(id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id,
                                        HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);

        Boolean result = service.deleteById(id);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestParam("page") Integer page,
                                     @RequestParam("size") Integer size,
                                     HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        Page<RegionDTO> result = service.getList(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/byLang/{lang}")
    public ResponseEntity<?> getByLang(@PathVariable("lang") String lang,
                                       HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        List<RegionShortDTO> result = service.getByLang(lang);
        return ResponseEntity.ok(result);
    }

}
