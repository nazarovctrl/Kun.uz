package com.example.kunuz.controller;

import com.example.kunuz.config.security.CustomUserDetail;
import com.example.kunuz.dto.auth.AdminRegistrationDTO;
import com.example.kunuz.dto.profile.*;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.ProfileService;
import com.example.kunuz.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService service;


    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @PostMapping("/admin/create")
    public ResponseEntity<?> create(@RequestBody AdminRegistrationDTO dto) {
        ProfileResponseDTO result = service.create(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/user/update")
    public ResponseEntity<?> updateUser(@RequestBody UserProfileUpdateDTO dto) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();

        ProfileResponseDTO result = service.updateUser(dto, user.getId());
        return ResponseEntity.ok(result);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<?> updateAdmin(@RequestBody AdminProfileUpdateDTO dto) {
        ProfileResponseDTO result = service.updateAdmin(dto);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/admin/list")
    public ResponseEntity<?> getList(
            @RequestParam("page") Integer page, @RequestParam("size") Integer size) {

        Page<ProfileResponseDTO> result = service.getList(page, size);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {


        Boolean result = service.deleteById(id);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/admin/filter")
    public ResponseEntity<?> filter(@RequestBody ProfileFilterDTO filterDTO,
                                    @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        List<ProfileDTO> filter = service.filter(filterDTO, page, size);
        return ResponseEntity.ok(filter);
    }


}
